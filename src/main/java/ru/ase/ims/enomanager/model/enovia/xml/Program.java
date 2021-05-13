package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Program {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "code")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
