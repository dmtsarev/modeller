package ru.ase.ims.enomanager.model.graph;

import ru.ase.ims.enomanager.model.enovia.DataModel;

import java.util.ArrayList;
import java.util.List;


public class GraphEntity {
    private final DataModel dataModel;
    private final List<GraphEntity> sourceList = new ArrayList<>();
    private final List<GraphEntity> targetList = new ArrayList<>();

    public GraphEntity(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Iterable<GraphEntity> getSourceObjectList() {
        return sourceList;
    }
    public void addSourceObject(GraphEntity entity) {
        sourceList.add(entity);
    }

    public Iterable<GraphEntity> getTargetObjectList() {
        return targetList;
    }
    public void addTargetObject(GraphEntity entity) {
        targetList.add(entity);
    }

    public DataModel getDataModel() {
        return dataModel;
    }
}
