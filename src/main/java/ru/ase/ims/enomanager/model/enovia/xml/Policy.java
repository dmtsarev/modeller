package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name ="policy")
public class Policy {

    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "adminProperties")
    private AdminProperties adminProperties;

    @XmlElement(name = "typeRefList")
    private TypeRefList typeRefList ;

    @XmlElement(name = "sequence")
     private String sequence;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id ;
    }

    public TypeRefList getTypeRef() { return typeRefList;}

    public void setTypeRefList(TypeRefList typeRefList) { this.typeRefList = typeRefList; }

    public void setSequence(String sequence) { this.sequence = sequence; }

    public String getSequence() { return sequence; }

    public AdminProperties getAdminProperties() { return adminProperties; }

    public void setAdminProperties (AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }}
