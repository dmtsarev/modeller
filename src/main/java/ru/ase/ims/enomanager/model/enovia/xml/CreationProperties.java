package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;

public class CreationProperties {

    @XmlElement(name = "release")
    private String release;
    @XmlElement(name = "datetime")
    private String dateTime;
    @XmlElement(name = "event")
    private String event;
    @XmlElement(name = "dtdInfo")
    private String dtdInfo;

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDtdInfo() {
        return dtdInfo;
    }

    public void setDtdInfo(String dtdInfo) {
        this.dtdInfo = dtdInfo;
    }
}
