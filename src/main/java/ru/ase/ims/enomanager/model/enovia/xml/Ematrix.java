package ru.ase.ims.enomanager.model.enovia.xml;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ematrix")
public class Ematrix {

    @XmlElement(name = "creationProperties")
    private CreationProperties creationProperties;

    @XmlElement(name = "type")
    @JsonIgnore
    private Type type;

    @XmlElement(name = "program")
    @JsonIgnore
    private Program program;

    @XmlElement(name = "interfaceType")
    private InterfaceType interfaceType;

    @XmlElement(name = "attributeDef")
    @JsonIgnore
    private AttributeDef  attributeDef;

    @XmlElement(name = "relationshipDef")
    private RelationshipDef relationshipDef;

    @XmlElement(name = "policy")
    private Policy policy;

    public Ematrix(Ematrix ematrix) {
        this.type = ematrix.type;
        this.attributeDef = ematrix.attributeDef;
        this.creationProperties = ematrix.creationProperties;
        this.interfaceType = ematrix.interfaceType;
        this.relationshipDef = ematrix.relationshipDef;
        this.program = ematrix.program;
        this.policy = ematrix.policy;
    }

    public Ematrix() {}

    public RelationshipDef getRelationshipDef() {
        return relationshipDef;
    }

    public void setRelationshipDef(RelationshipDef relationshipDef) {
        this.relationshipDef = relationshipDef;
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
    }

    public CreationProperties getCreationProperties() {
        return creationProperties;
    }

    public void setCreationProperties(CreationProperties creationProperties) {
        this.creationProperties = creationProperties;
    }

    public Policy getPolicy() { return policy; }

    public void setPolicy(Policy policy) { this.policy = policy; }

    public Type getType() {
        return type;
    }

    public Program getProgram() {
        return program;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AttributeDef getAttributeDef() {
        return attributeDef;
    }

    public void setAttributeDef(AttributeDef attributeDef) {
        this.attributeDef = attributeDef;
    }

    @Override
    public String toString() {
        return "Ematrix{" +
                "creationProperties=" + creationProperties +
                ", type=" + type +
                ", interfaceType=" + interfaceType +
                ", attributeDef=" + attributeDef +
                ", relationshipDef=" + relationshipDef +
                ", program=" + program +
                ", policy =" + policy +
                '}';
    }
}
