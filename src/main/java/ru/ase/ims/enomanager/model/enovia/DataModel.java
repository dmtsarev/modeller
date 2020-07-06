package ru.ase.ims.enomanager.model.enovia;

import ru.ase.ims.enomanager.model.graph.GraphVertexDTO;

import java.util.List;

public interface DataModel {
    String getName();
    String getModelType();
    List<GraphVertexDTO> getSourceList();
    List<GraphVertexDTO> getTargetList();

}
