package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributeDef {
    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "primitiveType")
    private String primitiveType;

    @XmlElement(name = "maxlength")
    private String maxlength;

    @XmlElement(name = "attrValueType")
    private String attrValueType;

    public String getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(String primitiveType) {
        this.primitiveType = primitiveType;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getAttrValueType() {
        return attrValueType;
    }

    public void setAttrValueType(String attrValueType) {
        this.attrValueType = attrValueType;
    }

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }
}
