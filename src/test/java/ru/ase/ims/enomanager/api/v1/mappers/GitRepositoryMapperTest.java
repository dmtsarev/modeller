package ru.ase.ims.enomanager.api.v1.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ase.ims.enomanager.api.v1.dto.GitRepositoryDTO;
import ru.ase.ims.enomanager.api.v1.dto.ProjectDTO;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.ProjectStatus;
import ru.ase.ims.enomanager.model.git.GitRepository;

import static org.junit.jupiter.api.Assertions.*;

class GitRepositoryMapperTest {

    Project project;
    ProjectDTO projectDTO;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1L);
        project.setProjectName("Test");
        project.setDescription("Test1");
        project.setStatus(ProjectStatus.CREATED);
        GitRepository gitRepository = new GitRepository();
        gitRepository.setId(1L);
        gitRepository.setUri("http://test");
        gitRepository.setUsername("user");
        gitRepository.setPassword("password");
        project.setGitRepository(gitRepository);

        projectDTO = new ProjectDTO();
        projectDTO.setProjectId(1L);
        projectDTO.setProjectName("Test");
        projectDTO.setDescription("Test1");
        projectDTO.setStatus("CREATED");
        GitRepositoryDTO gitRepositoryDTO = new GitRepositoryDTO();
        gitRepositoryDTO.setRepositoryId(1L);
        gitRepositoryDTO.setUri("http://test");
        gitRepositoryDTO.setUsername("user");
        gitRepositoryDTO.setPassword("password");
        projectDTO.setGit(gitRepositoryDTO);
        projectDTO.setGitPath("http://test");
        projectDTO.setGitConnectionId(1L);
    }

    @Test
    void repositoryToRepositoryDTO() {
        ProjectDTO newDTO = ProjectMapper.PROJECT_MAPPER.projectToProjectDTO(project);
    }

    @Test
    void repositoryDTOToRepository() {
        Project newProject = ProjectMapper.PROJECT_MAPPER.projectDTOToProject(projectDTO);
        assertEquals(1L, newProject.getId().longValue());
        assertEquals("Test", newProject.getProjectName());
        assertEquals("Test1", newProject.getDescription());
        assertEquals(ProjectStatus.CREATED, newProject.getStatus());
        assertEquals(1L, newProject.getGitRepository().getId().longValue());
        assertEquals("http://test", newProject.getGitRepository().getUri());
        assertEquals("user", newProject.getGitRepository().getUsername());
        assertEquals("password", newProject.getGitRepository().getPassword());
    }
}