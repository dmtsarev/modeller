package ru.ase.ims.enomanager.model.graph;

import java.io.Serializable;
import java.util.Objects;

public class GraphEdgeDTO implements Serializable {
    private String name;
    private String type;
    private String sourceName;
    private String targetName;

    public GraphEdgeDTO() {
    }

    public GraphEdgeDTO(Edge edge) {
        this.name = edge.getName();
        this.type = edge.getType();
        this.sourceName = edge.getSource().getName();
        this.targetName = edge.getTarget().getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEdgeDTO that = (GraphEdgeDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(sourceName, that.sourceName) &&
                Objects.equals(targetName, that.targetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, sourceName, targetName);
    }
}
