package ru.ase.ims.enomanager.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public interface FileService {
    List<String> listFiles(String path);
    InputStream readFile(String file) throws FileNotFoundException;
}
