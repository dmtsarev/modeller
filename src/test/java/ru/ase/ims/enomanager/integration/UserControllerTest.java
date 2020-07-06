package ru.ase.ims.enomanager.integration;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.Role;
import ru.ase.ims.enomanager.model.User;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Disabled
public class UserControllerTest extends AbstractSpringControllerTest {

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Transactional
    public void createUserTest() {

        Role role = new Role();
        role.setMnemonic("analyst");
        role.setRoleName("analyst");
        repository.roleRepository.save(role);

        User user = new User("test_user", "123", role);

        var response = controller.userController.createUser("test_user", "123", "analyst", null);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var userFormDB = repository.userRepository.findByUserName("test_user").get();
        assertEquals(user.getUserName(), userFormDB.getUserName());
        assertEquals(user.getRole().getRoleName(), userFormDB.getRole().getRoleName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Transactional
    public void createUserWithProjectTest() {

        Role role = new Role();
        role.setMnemonic("analyst");
        role.setRoleName("analyst");
        repository.roleRepository.save(role);
        repository.projectRepository.deleteAll();
        Project project = new Project();
        project.setProjectName("test_project_name");
        project.setDescription("description_Project");
        project.setId(1L);
        repository.projectRepository.save(project);

        User user = new User("test_user_2", "123", role, project);

        var response = controller.userController.createUser("test_user_2", "123", "analyst", 1L);
        //TODO to fix
        //assertEquals(HttpStatus.OK, response.getStatusCode());

        /*var userFormDB = repository.userRepository.findByUserName("test_user_2").get();
        assertEquals(user.getUserName(), userFormDB.getUserName());
        assertEquals(user.getProject().getProjectName(), userFormDB.getProject().getProjectName());
        assertEquals(user.getProject().getDescription(), userFormDB.getProject().getDescription());
        assertEquals(user.getProject().getId(), userFormDB.getProject().getId());
        assertEquals(user.getRole().getRoleName(), userFormDB.getRole().getRoleName());*/
    }

    @Test
    @Transactional
    public void updateUserTest() {

        var business_user = repository.userRepository.findByUserName("business_user").get();

        var response = controller.userController.update(business_user.getId(), "analyst");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var userFormDB = repository.userRepository.findByUserName("business_user").get();
        assertEquals("ANALYST", userFormDB.getRole().getRoleName());
    }

    @Test
    @Transactional
    public void usersTest() {
        assertFalse(controller.userController.users("").getBody().isEmpty());
    }

    @Test
    @Transactional
    public void usersOnRoleTest() {
        assertFalse(controller.userController.users("admin").getBody().isEmpty());
    }
}
