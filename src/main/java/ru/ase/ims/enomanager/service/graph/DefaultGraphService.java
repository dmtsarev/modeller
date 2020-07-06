package ru.ase.ims.enomanager.service.graph;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.enovia.*;
import ru.ase.ims.enomanager.model.enovia.xml.AdminProperties;
import ru.ase.ims.enomanager.model.enovia.xml.AttributeDef;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.model.enovia.xml.Type;
import ru.ase.ims.enomanager.model.graph.*;

import java.util.*;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class DefaultGraphService implements GraphService {
    private final Map<Long, Map<String, GraphEntity>> releases;
    public DefaultGraphService() {
        releases = new HashMap<>();
    }

    @Override
    public List<Long> getCachedGraphs() {
        return new ArrayList<>(this.releases.keySet());
    }

    @Override
    public boolean buildGraph(Long releaseId, Collection<EnoviaEntity> items) {
        Map<String, DataModel> modelsMap = new HashMap<>();
        Map<String, GraphEntity> entityGraph = new HashMap<>();
        releases.put(releaseId, entityGraph);
        items.forEach(item -> {
            DataModel model = (DataModel) item.getEmatrix();
            if (model != null) {
                modelsMap.put(model.getName(), model);
                entityGraph.put(model.getName(), new GraphEntity(model));
            }
        });
        modelsMap.values().forEach(item -> {
            BiConsumer<GraphVertexDTO, Boolean> addToCache = (value, isSource) -> {
                GraphEntity entity = entityGraph.get(value.toString());
                DataModel tmpModel = null;
                if (entity == null) {
                    Ematrix ematrix = new Ematrix();
                    switch (value.getType()) {
                        case EnoviaAttribute.OBJECT_TYPE: {
                            ematrix.setAttributeDef(new AttributeDef());
                            ematrix.getAttributeDef().setAdminProperties(new AdminProperties());
                            ematrix.getAttributeDef().getAdminProperties().setName(value.toString());
                            tmpModel = new EnoviaAttribute(ematrix);
                            break;
                        }
                        case EnoviaType.OBJECT_TYPE: {
                            ematrix.setType(new Type());
                            ematrix.getType().setAdminProperties(new AdminProperties());
                            ematrix.getType().getAdminProperties().setName(value.toString());
                            tmpModel = new EnoviaType(ematrix);
                            break;
                        }
                    }

                    entity = new GraphEntity(tmpModel);
                }
                GraphEntity secondEntity = entityGraph.get(item.getName());
                if (isSource) {
                    entity.addTargetObject(secondEntity);
                    secondEntity.addSourceObject(entity);
                } else {
                    secondEntity.addTargetObject(entity);
                    entity.addSourceObject(secondEntity);
                }
            };

            item.getSourceList().forEach(value -> addToCache.accept(value, true));
            item.getTargetList().forEach(value -> addToCache.accept(value, false));
        });
        return false;
    }

    @Override
    public EntityGraph getGraphForGraphEntity(Long releaseId, String entityName) {

        if (Optional.ofNullable(releaseId).isEmpty()) {
            log.info("BranchName is empty or null");
            return null;
        }

        if (Optional.ofNullable(entityName).isEmpty()) {
            log.info("EntityName is empty or null");
            return null;
        }

        if (Optional.ofNullable(releases.get(releaseId)).isEmpty()) {
            log.info("BranchName not found to releases" + releaseId);
            return null;
        }

        GraphEntity graphEntity = releases.get(releaseId).get(entityName);
        GraphBuilder graphBuilder = new GraphBuilder();
        return graphBuilder.getGraph(graphEntity);
    }

}
