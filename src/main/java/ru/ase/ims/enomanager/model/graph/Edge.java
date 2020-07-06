package ru.ase.ims.enomanager.model.graph;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ase.ims.enomanager.model.enovia.DataModel;

@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    private String name;
    private String type;
    private DataModel source;
    private DataModel target;

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

    public DataModel getSource() {
        return source;
    }

    public void setSource(DataModel source) {
        this.source = source;
    }

    public DataModel getTarget() {
        return target;
    }

    public void setTarget(DataModel target) {
        this.target = target;
    }
}
