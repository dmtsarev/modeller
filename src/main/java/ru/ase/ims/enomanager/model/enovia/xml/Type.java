package ru.ase.ims.enomanager.model.enovia.xml;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Type {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "derivedFrom")
    @JsonIgnore
    private DerivedFrom derivedFrom;

    @XmlElement(name = "attributeDefRefList")
    private AttributeList attributeList;

    @XmlElement(name = "triggerList")
    private TriggerList triggerList;

    @XmlElement(name = "typeKind")
    private String typeKind;

    public String getTypeKind() {
        return typeKind;
    }

    public void setTypeKind(String typeKind) {
        this.typeKind = typeKind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DerivedFrom getDerivedFrom() {
        return derivedFrom;
    }

    public void setDerivedFrom(DerivedFrom derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public AttributeList getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(AttributeList attributeList) {
        this.attributeList = attributeList;
    }

    public TriggerList getTriggerList() {
        return triggerList;
    }

    public void setTriggerList(TriggerList triggerList) {
        this.triggerList = triggerList;
    }
}
