package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;

public class Side {
    @XmlElement(name = "cardinality")
    private String cardinality;
    @XmlElement(name = "revisionAction")
    private String revisionAction;
    @XmlElement(name = "cloneAction")
    private String cloneAction;
    @XmlElement(name = "typeRefList")
    private TypeRefList typeRefList;

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getRevisionAction() {
        return revisionAction;
    }

    public void setRevisionAction(String revisionAction) {
        this.revisionAction = revisionAction;
    }

    public String getCloneAction() {
        return cloneAction;
    }

    public void setCloneAction(String cloneAction) {
        this.cloneAction = cloneAction;
    }

    public TypeRefList getTypeRefList() {
        return typeRefList;
    }

    public void setTypeRefList(TypeRefList typeRefList) {
        this.typeRefList = typeRefList;
    }
}
