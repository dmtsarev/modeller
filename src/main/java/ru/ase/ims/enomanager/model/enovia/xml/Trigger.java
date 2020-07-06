package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trigger {
    @XmlElement(name = "triggerName")
    private String triggerName;
    @XmlElement(name = "programRef")
    private String programRef;
    @XmlElement(name = "inputArguments")
    private String inputArguments;

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getProgramRef() {
        return programRef;
    }

    public void setProgramRef(String programRef) {
        this.programRef = programRef;
    }

    public String getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(String inputArguments) {
        this.inputArguments = inputArguments;
    }
}
