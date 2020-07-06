package ru.ase.ims.enomanager.model.graph;

import ru.ase.ims.enomanager.model.enovia.DataModel;

import java.io.Serializable;
import java.util.Objects;

public class GraphVertexDTO implements Serializable {
    private String name;
    private String type;

    public GraphVertexDTO() {
    }
    public GraphVertexDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public GraphVertexDTO(DataModel dataModel) {
        this.name = dataModel.getName();
        this.type = dataModel.getModelType();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertexDTO that = (GraphVertexDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return name;
    }
}
