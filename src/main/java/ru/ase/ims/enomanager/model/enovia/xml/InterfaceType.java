package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "interfaceType")
public class InterfaceType {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "derivedFromInterface")
    private DerivedFromInterface derivedFromInterface;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public DerivedFromInterface getDerivedFromInterface() {
        return derivedFromInterface;
    }

    public void setDerivedFromInterface(DerivedFromInterface derivedFromInterface) {
        this.derivedFromInterface = derivedFromInterface;
    }
}
