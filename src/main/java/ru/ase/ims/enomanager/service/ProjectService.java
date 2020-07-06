package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();
    Optional<Project> getProjectById(Long projectId);
    Project createProject(Project project);
}
