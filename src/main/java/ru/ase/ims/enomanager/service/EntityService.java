package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.graph.EntityGraph;

import javax.transaction.Transactional;
import java.util.List;

public interface EntityService {
    List<EnoviaEntity> getEntityList(Long releaseId, String type, String searchWord);
    List<EnoviaEntity> findEntities(Release release);
    EnoviaEntity updateEntity(Long entityId, EnoviaEntity enoviaEntity);
    @Transactional
    EntityGraph getEntityGraph(Long entityId);
    EnoviaEntity getEntity(Long entityId);
}
