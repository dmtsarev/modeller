package ru.ase.ims.enomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DefaultFileService implements FileService {
    @Override
    public List<String> listFiles(String path) {
        log.debug("Path for listFiles: " + path);
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            return paths.filter(Files::isRegularFile).filter(item -> !item.toString().contains(".git"))
                    .map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            log.info("Get list files on path: " + path + "with exception " + e);
        }
        return null;
    }

    @Override
    public InputStream readFile(String file) throws FileNotFoundException
    {
        log.debug("File for read: " + file);
        return new FileInputStream(new File(file));
    }
}
