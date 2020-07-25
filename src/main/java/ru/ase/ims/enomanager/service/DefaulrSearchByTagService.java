package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.repository.EntityRepository;
import ru.ase.ims.enomanager.repository.ReleaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DefaulrSearchByTagService implements SearchByTagService {
    private final EntityRepository entityRepository;
    private final ReleaseRepository releaseRepository;

    public DefaulrSearchByTagService(EntityRepository entityRepository, ReleaseRepository releaseRepository) {
        this.entityRepository = entityRepository;
        this.releaseRepository = releaseRepository;
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
