package ru.ase.ims.enomanager.service;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.enovia.DataModel;
import ru.ase.ims.enomanager.model.graph.EntityGraph;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.repository.EntityRepository;
import ru.ase.ims.enomanager.service.git.GitClient;
import ru.ase.ims.enomanager.service.git.GitManager;
import ru.ase.ims.enomanager.service.graph.GraphService;
import ru.ase.ims.enomanager.service.xml.XMLReader;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DefaultEntityService implements EntityService {

    private final EntityRepository entityRepository;
    private final XMLReader xmlReader;
    private final GitManager gitManager;
    private final GraphService graphService;
    private final Map<Long, Map<Long, EnoviaEntity>> releasesCache = new HashMap<>();

    public DefaultEntityService(EntityRepository entityRepository, XMLReader xmlReader,
                                GitManager gitManager, GraphService graphService) {
        this.entityRepository = entityRepository;
        this.xmlReader = xmlReader;
        this.gitManager = gitManager;
        this.graphService = graphService;
    }

    @Override
    public List<EnoviaEntity> getEntityList(Long releaseId, String type, String searchWord) {

        if(searchWord == null) {
            return getEnoviaEntities(releaseId, type, searchWord);
        } else if(searchWord.isBlank()) {
            return getEnoviaEntities(releaseId, type, searchWord);
        } else {
            return entityRepository.findAllByReleaseIdAndTypeAndSearchWord(releaseId, type, searchWord);
        }
    }

    private List<EnoviaEntity> getEnoviaEntities(Long releaseId, String type, String searchWord) {
        List<EnoviaEntity> resultList = new ArrayList<>();
        if(releasesCache.containsKey(releaseId)) {
            releasesCache.get(releaseId).values().forEach(item -> {
                if(item.getType().equals(type)) {
                    if(searchWord != null && item.getFileName().contains(searchWord)) {
                        resultList.add(item);
                    } else if (searchWord == null) {
                        resultList.add(item);
                    }
                } else if(type == null) {
                    resultList.add(item);
                }
            });
        }
        return resultList;
    }

    @Override
    @Transactional
    public List<EnoviaEntity> findEntities(Release release) {
        Map<Long, EnoviaEntity> releaseCache = new HashMap<>();
        if (release == null) return Collections.emptyList();
        GitClient gitClient= gitManager.getGitClient(release.getProject().getGitRepository().getId());
        Map<String, EnoviaEntity> entitiesMap = new HashMap<>();
        entityRepository.findAllByReleaseId(release.getId())
                .forEach(item -> entitiesMap.put(item.getFileName(), item));
        synchronized (GitClient.class) {
            try {
                gitClient.checkoutBranch(release.getBranchName());
                TreeWalk treeWalk = gitClient.getBranchTree();
                while (treeWalk.next()) {
                    if (treeWalk.isSubtree()) {
                        System.out.println("dir: " + treeWalk.getPathString());
                        treeWalk.enterSubtree();
                    } else {
                        ObjectId objectId = treeWalk.getObjectId(0);
                        EnoviaEntity tmpEntity;
                        String fileContent = new String(gitClient.getRepository()
                                .open(objectId).openStream().readAllBytes(), StandardCharsets.UTF_8);
                        if(!entitiesMap.containsKey(treeWalk.getPathString())) {
                            tmpEntity = createEntity(treeWalk.getPathString(), fileContent, release);
                        } else {
                            tmpEntity = this.entityRepository.getOne(entitiesMap.get(treeWalk.getPathString()).getId());
                            DataModel vertex = xmlReader.getObjectFromString(tmpEntity.getType(), fileContent);
                            tmpEntity.setEmatrix((Ematrix) vertex);
                        }
                        if (tmpEntity != null) {
                            releaseCache.put(tmpEntity.getId(), tmpEntity);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.graphService.buildGraph(release.getId(), releaseCache.values());
        this.releasesCache.put(release.getId(), releaseCache);
        return entityRepository.findAllByReleaseId(release.getId());
    }
    private EnoviaEntity createEntity(String pathString, String fileContent, Release release) {
        String type = pathString.split("_")[0];
        DataModel vertex = xmlReader.getObjectFromString(type, fileContent);
        if (vertex != null) {
            EnoviaEntity tmpEntity =  entityRepository.save(new EnoviaEntity(vertex.getName(), vertex.getModelType(), pathString, release));
            tmpEntity.setEmatrix((Ematrix) vertex);
            return tmpEntity;
        }
        return null;
    }

    @Override
    public EnoviaEntity updateEntity(Long entityId, EnoviaEntity enoviaEntityNew) {
        EnoviaEntity enoviaEntity = getEntity(entityId);
        enoviaEntity.setTags(enoviaEntityNew.getTags());
        enoviaEntity.setDescription(enoviaEntityNew.getDescription());
        entityRepository.save(enoviaEntity);
        return enoviaEntity;
    }

    @Override
    @Transactional
    public EntityGraph getEntityGraph(Long entityId) {
        EnoviaEntity item = getEntity(entityId);
        return graphService.getGraphForGraphEntity(item.getRelease().getId(), item.getEntityName());
    }

    @Override
    public EnoviaEntity getEntity(Long entityId) {
        for(Map<Long, EnoviaEntity> tmpMap: releasesCache.values()) {
            if(tmpMap.containsKey(entityId)) {
                var entity = tmpMap.get(entityId);
                entity.setEmatrixHTML(xmlReader.getHtml(entity.getEmatrix()));
                return entity;
            }
        }
        return null;
    }
}
