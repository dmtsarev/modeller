package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;

public class DateInfo {
    @XmlElement(name = "datetime")
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
