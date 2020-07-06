package ru.ase.ims.enomanager.model.enovia.xml;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminProperties {
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "creationInfo")
    private DateInfo creationInfo;

    @XmlElement(name = "modificationInfo")
    private DateInfo modificationInfo;

    @XmlElement(name = "propertyList")
    @JsonIgnore
    private PropertyList propertyList;

    @XmlElement(name = "historyList")
    @JsonIgnore
    private HistoryList historyList;

    public DateInfo getCreationInfo() {
        return creationInfo;
    }

    public void setCreationInfo(DateInfo creationInfo) {
        this.creationInfo = creationInfo;
    }

    public DateInfo getModificationInfo() {
        return modificationInfo;
    }

    public void setModificationInfo(DateInfo modificationInfo) {
        this.modificationInfo = modificationInfo;
    }

    public HistoryList getHistoryList() {
        return historyList;
    }

    public void setHistoryList(HistoryList historyList) {
        this.historyList = historyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyList getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(PropertyList propertyList) {
        this.propertyList = propertyList;
    }
}
