package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Property {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "value")
    private String value;
    @XmlElement(name = "flags")
    private String flags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}
