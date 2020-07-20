package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.repository.EntityRepository;
import ru.ase.ims.enomanager.repository.ReleaseRepository;

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
        //return entityRepository.findDistinctByTagsNameIn(tags);
        return entityRepository.findDistinctByTagsIdIn(tags);
    }

    @Override
    public List<EnoviaEntity> getEntityListByReleases(Set<Long> tags, Set<Long> releases) {
        return entityRepository.findDistinctByTagsIdInAndReleaseIdIn(tags, releases);
    }

    @Override
    public List<Long> getReleaseList() {
        return releaseRepository.findId();
    }
}
