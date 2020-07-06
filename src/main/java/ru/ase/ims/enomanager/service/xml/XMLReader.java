package ru.ase.ims.enomanager.service.xml;

import ru.ase.ims.enomanager.model.enovia.DataModel;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;

public interface XMLReader {
    DataModel getObjectFromFile(String branchName, String fileName);
    DataModel getObjectFromString(String type, String content);
    String getHtml(Ematrix xmlContent);
    String getXml(Ematrix content);
}
