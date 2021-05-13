package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "role")
public class Role {
    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "roleType")
    private String roleType;

    @XmlElement(name = "maturity")
    private String maturity;

    @XmlElement(name = "category")
    private String category;

    @XmlElement(name = "parentRole")
    private ParentRole parentRole;

    @XmlAttribute(name = "id")
    private String id;

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getMaturity() {
        return maturity;
    }

    public void setMaturity(String maturity) {
        this.maturity = maturity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ParentRole getParentRole() {
        return parentRole;
    }

    public void setParentRole(ParentRole parentRole) {
        this.parentRole = parentRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
