package ru.ase.ims.enomanager.api.v1.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ase.ims.enomanager.api.v1.dto.ProjectDTO;
import ru.ase.ims.enomanager.api.v1.dto.ReleaseDTO;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.Release;


@Mapper
public interface ProjectMapper {
    ProjectMapper PROJECT_MAPPER = Mappers.getMapper(ProjectMapper.class);
    @Mapping(target = "git", ignore = true)
    @Mapping(source = "id", target = "projectId")
    @Mapping(source = "releases", target = "releases")
    @Mapping(source = "gitRepository.uri", target = "gitPath")
    @Mapping(source = "gitRepository.id", target = "gitConnectionId")
    ProjectDTO projectToProjectDTO(Project project);

    @Mapping(source = "id", target = "releaseId")
    ReleaseDTO releaseToReleaseDTO(Release release);

    @InheritInverseConfiguration
    @Mapping(source = "git", target = "gitRepository")
    Project projectDTOToProject(ProjectDTO projectDTO);
}
