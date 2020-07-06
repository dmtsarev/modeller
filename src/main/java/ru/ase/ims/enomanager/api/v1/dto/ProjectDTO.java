package ru.ase.ims.enomanager.api.v1.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private long projectId;
    private String projectName;
    private String description;
    private List<ReleaseDTO> releases;
    private String gitPath;
    private long gitConnectionId;
    private String status;
    private GitRepositoryDTO git;
}
