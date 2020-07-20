package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface SearchByTagService {
    List<EnoviaEntity> getEntityList(Set<Long> tags);
    List<EnoviaEntity> getEntityListByReleases(Set<Long> tags, Set<Long> releases);
    List<Long> getReleaseList();
}
