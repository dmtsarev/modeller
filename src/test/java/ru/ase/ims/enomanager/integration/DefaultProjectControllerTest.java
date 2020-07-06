package ru.ase.ims.enomanager.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.HttpStatus;
import ru.ase.ims.enomanager.model.Project;

import java.util.Optional;

import static org.junit.Assert.*;

@Disabled
public class DefaultProjectControllerTest extends AbstractSpringControllerTest {

    @Before
    public void addDataToDB() {
        Project project = new Project();
        project.setProjectName("Test_Project_Name");

        repository.projectRepository.save(project);
    }

    @After
    public void clearDB() {
        repository.projectRepository.deleteAll();
    }

    @Test
    public void getAllProjects() {
        assertFalse(controller.defaultProjectController.getAllProjects().isEmpty());
    }

    @Test
    public void getProjectById() {
        Optional<Project> projectRepository = repository.projectRepository.findAll().stream().findFirst();
        assertTrue(projectRepository.isPresent());

        assertEquals(HttpStatus.OK, controller.defaultProjectController.getProjectById(projectRepository.get().getId()).getStatusCode());
    }
}
