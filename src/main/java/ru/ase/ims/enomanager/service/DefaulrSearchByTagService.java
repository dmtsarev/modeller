package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.repository.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DefaulrSearchByTagService implements SearchByTagService {
    private final EntityRepository entityRepository;

    public DefaulrSearchByTagService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public List<EnoviaEntity> getEntityList(Set<String> tags) {
        return entityRepository.findDistinctByTagsNameIn(tags);
    }
}
