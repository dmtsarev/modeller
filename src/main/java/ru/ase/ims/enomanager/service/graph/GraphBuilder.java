package ru.ase.ims.enomanager.service.graph;

import ru.ase.ims.enomanager.model.enovia.EnoviaAttribute;
import ru.ase.ims.enomanager.model.enovia.EnoviaInterface;
import ru.ase.ims.enomanager.model.enovia.EnoviaRelationship;
import ru.ase.ims.enomanager.model.enovia.EnoviaType;
import ru.ase.ims.enomanager.model.graph.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphBuilder {
    final Set<GraphVertexDTO> vertexDTOList = new HashSet<>();
    final Set<GraphEdgeDTO> edgeDTOList = new HashSet<>();
    public EntityGraph getGraph(GraphEntity graphEntity) {
        switch (graphEntity.getDataModel().getModelType()) {
            case EnoviaType.OBJECT_TYPE: {
                buildFromVertex(graphEntity);
                break;
            }
            case EnoviaRelationship.OBJECT_TYPE: {
                buildFromEdge(graphEntity);
                break;
            }
            case EnoviaAttribute.OBJECT_TYPE: {
                buildFromVertex(graphEntity);
                break;
            }
            case EnoviaInterface.OBJECT_TYPE: {
                buildFromVertex(graphEntity);
                break;
            }
        }
        return new EntityGraph(new ArrayList<>(vertexDTOList), new ArrayList<>(edgeDTOList));
    }
    private void buildFromVertex(GraphEntity graphEntity) {
        vertexDTOList.add(new GraphVertexDTO(graphEntity.getDataModel()));
        graphEntity.getSourceObjectList().forEach(sourceObject -> {
            switch ((sourceObject.getDataModel().getModelType())) {
                case EnoviaType.OBJECT_TYPE: {
                    handleVertex(graphEntity, sourceObject);
                    break;
                }
                case EnoviaAttribute.OBJECT_TYPE: {
                    break;
                }
                case EnoviaInterface.OBJECT_TYPE: {
                    handleVertex(graphEntity, sourceObject);
                    break;
                }
                case EnoviaRelationship.OBJECT_TYPE: {
                    handleEdge(graphEntity, sourceObject);
                    break;
                }
            }
        });
        graphEntity.getTargetObjectList().forEach(sourceObject -> {
            switch ((sourceObject.getDataModel().getModelType())) {
                case EnoviaType.OBJECT_TYPE: {
                    handleVertex(graphEntity, sourceObject);
                    break;
                }
                case EnoviaAttribute.OBJECT_TYPE: {
                    //handleVertex(graphEntity, sourceObject);
                    break;
                }
                case EnoviaInterface.OBJECT_TYPE: {
                    handleVertex(graphEntity, sourceObject);
                    break;
                }
                case EnoviaRelationship.OBJECT_TYPE: {
                    sourceObject.getTargetObjectList().forEach(item -> handleEdge(graphEntity, sourceObject));
                    break;

                }
            }
        });
    }
    private void buildFromEdge(GraphEntity graphEntity){

    }
    private void handleVertex(GraphEntity graphEntity, GraphEntity sourceObject) {
        vertexDTOList.add(new GraphVertexDTO(sourceObject.getDataModel()));
        edgeDTOList.add(new GraphEdgeDTO(new Edge( sourceObject.getDataModel().getName() + " " +
                graphEntity.getDataModel().getName(), "inheritance", sourceObject.getDataModel(),
                graphEntity.getDataModel())));
    }
    private void handleEdge(GraphEntity graphEntity, GraphEntity sourceObject) {
        sourceObject.getTargetObjectList().forEach(targetObject -> {
            edgeDTOList.add(new GraphEdgeDTO(new Edge(sourceObject.getDataModel().getName(),
                    "relation", graphEntity.getDataModel(), targetObject.getDataModel())));
            vertexDTOList.add(new GraphVertexDTO(targetObject.getDataModel()));
        });
    }

}
