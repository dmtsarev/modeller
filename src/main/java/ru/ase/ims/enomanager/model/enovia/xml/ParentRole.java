package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ParentRole {
    @XmlElement(name = "roleRef")
    private String roleRef;

    @XmlAttribute(name = "count")
    private Integer size;

    public String getRoleRef() {
        return roleRef;
    }

    public void setRoleRef(String roleRef) {
        this.roleRef = roleRef;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
