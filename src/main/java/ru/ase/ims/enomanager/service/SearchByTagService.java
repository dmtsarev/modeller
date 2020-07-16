package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.EnoviaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface SearchByTagService {
    List<EnoviaEntity> getEntityList(Set<String> tags);
}
