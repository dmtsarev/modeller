package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "attributeDefRefList")
public class AttributeList {
    @XmlElement(name = "attributeDefRef")
    private List<String> attributes;

    @XmlAttribute(name = "count")
    private Integer size;

    public List<String> getAttributes() {
        return attributes;
    }
}
