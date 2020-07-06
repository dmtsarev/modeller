package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DerivedFrom {

    @XmlElement(name = "typeRefList")
    private TypeRefList typeRefList;

    public TypeRefList getTypeRefList() {
        return typeRefList;
    }

    public void setTypeRefList(TypeRefList typeRefList) {
        this.typeRefList = typeRefList;
    }
}
