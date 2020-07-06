package ru.ase.ims.enomanager.model.enovia.xml;

import javax.xml.bind.annotation.XmlElement;

public class DerivedFromInterface {
    @XmlElement(name = "interfaceTypeRefList")
    private InterfaceTypeRefList interfaceTypeRefList;

    public InterfaceTypeRefList getInterfaceTypeRefList() {
        return interfaceTypeRefList;
    }

    public void setInterfaceTypeRefList(InterfaceTypeRefList interfaceTypeRefList) {
        this.interfaceTypeRefList = interfaceTypeRefList;
    }
}
