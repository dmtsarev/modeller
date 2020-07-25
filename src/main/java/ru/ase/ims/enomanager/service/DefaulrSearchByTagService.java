package ru.ase.ims.enomanager.service;

import org.apache.poi.ss.formula.functions.T;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.model.enovia.DataModel;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.repository.EntityRepository;
import ru.ase.ims.enomanager.repository.ReleaseRepository;
import ru.ase.ims.enomanager.service.git.GitClient;
import ru.ase.ims.enomanager.service.git.GitManager;
import ru.ase.ims.enomanager.service.xml.XMLReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DefaulrSearchByTagService implements SearchByTagService {
    private final EntityRepository entityRepository;
    private final ReleaseRepository releaseRepository;
    private final GitManager gitManager;
    private final XMLReader xmlReader;

    public DefaulrSearchByTagService(EntityRepository entityRepository, ReleaseRepository releaseRepository, GitManager gitManager, XMLReader xmlReader) {
        this.entityRepository = entityRepository;
        this.releaseRepository = releaseRepository;
        this.gitManager = gitManager;
        this.xmlReader = xmlReader;
    }

    @Override
    public List<EnoviaEntity> getEntityList(Set<Long> tags) {
        List<EnoviaEntity> enoviaEntities = entityRepository.findDistinctByTagsIdIn(tags);
        return getEnoviaEntities(tags, enoviaEntities);
    }

    @Override
    public List<EnoviaEntity> getEntityListByReleases(Set<Long> tags, Set<Long> releases) {
        List<EnoviaEntity> enoviaEntities = entityRepository.findDistinctByTagsIdInAndReleaseIdIn(tags, releases);
        return getEnoviaEntities(tags, enoviaEntities);
    }

    @Override
    public List<Long> getReleaseList() {
        return releaseRepository.findId();
    }

    @Override
    public EnoviaEntity getEntity(Long entityId) throws IOException, GitAPIException {
        EnoviaEntity enoviaEntity = entityRepository.findById(entityId).orElse(null);
        GitClient gitClient = gitManager.getGitClient(enoviaEntity.getRelease().getProject().getGitRepository().getId());
        EnoviaEntity tmpEntity;

        synchronized (GitClient.class){
            TreeWalk treeWalk = gitClient.getBranchTreeByName(enoviaEntity.getRelease().getBranchName(), enoviaEntity.getFileName());
            treeWalk.next();
            ObjectId objectId = treeWalk.getObjectId(0);
            String fileContent = new String(gitClient.getRepository()
                    .open(objectId).openStream().readAllBytes(), StandardCharsets.UTF_8);
            String type = enoviaEntity.getFileName().split("_")[0];
            DataModel vertex = xmlReader.getObjectFromString(type, fileContent);
            tmpEntity =  new EnoviaEntity(vertex.getName(), vertex.getModelType(), treeWalk.getPathString(), enoviaEntity.getRelease());
            tmpEntity.setEmatrix((Ematrix) vertex);
            tmpEntity.setEmatrixHTML(xmlReader.getHtml(tmpEntity.getEmatrix()));
        }
        return tmpEntity;
    }

    private List<EnoviaEntity> getEnoviaEntities(Set<Long> tags, List<EnoviaEntity> enoviaEntities) {
        ArrayList<EnoviaEntity> result = new ArrayList<>();

        for (EnoviaEntity e : enoviaEntities){
            if(e.getTags().size() >= tags.size()){
                Set<Tag> eTags = e.getTags();
                int count = 0;
                for (Tag eTag : eTags) {
                    for (Long selectedTagId : tags){
                        if(selectedTagId.equals(eTag.getId())) {
                            count++;
                        }
                    }
                }
                if(count == tags.size()){
                    result.add(e);
                }
            }
        }

        return result;
    }
}
