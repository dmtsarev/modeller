package ru.ase.ims.enomanager.service.graph;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.enovia.DataModel;
import ru.ase.ims.enomanager.service.xml.XMLReader;

@Service
public class DefaultDataModelFactory implements DataModelFactory {

    private final XMLReader xmlReader;

    public DefaultDataModelFactory(XMLReader xmlReader) {
        this.xmlReader = xmlReader;
    }

    @Override
    public DataModel createInstance(String branchName, String fileName) {
        return xmlReader.getObjectFromFile(branchName, fileName);
    }
}
