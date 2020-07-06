package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.api.v1.dto.ProjectDTO;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.service.ProjectService;
import ru.ase.ims.enomanager.api.v1.mappers.ProjectMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "${application.v1API}/projects")
@RequiredArgsConstructor
public class DefaultProjectController {

    private final ProjectService projectService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Loads releases list and cached releases names",
            response = Release.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error", response = List.class)
    })
    @Transactional
    public List<ProjectDTO> getAllProjects() {
       return projectService.getAllProjects().stream().map(ProjectMapper.PROJECT_MAPPER::projectToProjectDTO).collect(Collectors.toList());
    }

    @GetMapping (
            path = "/{projectId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Load release and cache all data",
            response = Release.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Release cached"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @Transactional
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable(name = "projectId") Long projectId ) {
        return projectService.getProjectById(projectId).map(e -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(ProjectMapper.PROJECT_MAPPER.projectToProjectDTO(e))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(

    )
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        return ProjectMapper.PROJECT_MAPPER.projectToProjectDTO(projectService.createProject(
                ProjectMapper.PROJECT_MAPPER.projectDTOToProject(projectDTO)
        ));
    }
}
