package ru.ase.ims.enomanager.service.graph;

import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.graph.EntityGraph;

import java.util.Collection;
import java.util.List;

public interface GraphService {
    boolean buildGraph(Long releaseId, Collection<EnoviaEntity> items);
    List<Long> getCachedGraphs();
    EntityGraph getGraphForGraphEntity(Long ReleaseId, String entityName);
}
