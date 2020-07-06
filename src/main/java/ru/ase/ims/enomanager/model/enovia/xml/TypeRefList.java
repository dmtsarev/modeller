package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TypeRefList {

    @XmlElement(name = "typeRef")
    List<String> typeRef;

    public List<String> getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(List<String> typeRef) {
        this.typeRef = typeRef;
    }
}
