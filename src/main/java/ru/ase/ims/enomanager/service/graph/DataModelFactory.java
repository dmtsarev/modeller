package ru.ase.ims.enomanager.service.graph;

import ru.ase.ims.enomanager.model.enovia.DataModel;

public interface DataModelFactory {
    DataModel createInstance(String branchName, String fileName);
}
