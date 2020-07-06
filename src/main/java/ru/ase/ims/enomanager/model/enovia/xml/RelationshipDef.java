package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;

public class RelationshipDef {
    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;
    @XmlElement(name = "fromSide")
    private Side fromSide;
    @XmlElement(name = "toSide")
    private Side toSide;

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public Side getFromSide() {
        return fromSide;
    }

    public void setFromSide(Side fromSide) {
        this.fromSide = fromSide;
    }

    public Side getToSide() {
        return toSide;
    }

    public void setToSide(Side toSide) {
        this.toSide = toSide;
    }
}
