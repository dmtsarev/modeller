package ru.ase.ims.enomanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.Role;
import ru.ase.ims.enomanager.model.User;
import ru.ase.ims.enomanager.repository.ProjectRepository;
import ru.ase.ims.enomanager.repository.RoleRepository;
import ru.ase.ims.enomanager.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;

    public Optional<User> createUser(String userName, String password, String roleMnemonic) {
        return roleRepository.findByMnemonic(roleMnemonic).map(role -> {
            User user = new User(userName, passwordEncoder(password), role);
            log.debug("Success create user with userName: " + userName);
            return Optional.of(userRepository.save(user));
        }).orElse(Optional.empty());
    }

    public Optional<User> createUser(String userName, String password, String roleMnemonic, Long projectId) {
        return roleRepository.findByMnemonic(roleMnemonic).map(role -> {
            User user = new User(userName, passwordEncoder(password), role);
            log.debug("Success create user with userName: " + userName);
            return userWithProject(projectId, user);
        }).orElse(Optional.empty());
    }

    public Optional<User> updateUserRole(Long id, String roleMnemonic) {

        if (!checkRole(roleMnemonic)) {
            log.info("Role name: " + roleMnemonic + "not found");
            return Optional.empty();
        }

        if (userRepository.findById(id).isPresent()) {
            Optional<Role> role = roleRepository.findByMnemonic(roleMnemonic);
            if (role.isPresent()) {
                User user = userRepository.findById(id).get();
                user.setRole(role.get());
                return Optional.of(userRepository.save(user));
            } else {
                return Optional.empty();
            }
        } else {
            log.info("User with id: " + id + "not found");
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> getUsersOnRole(String roleMnemonic) {
        return userRepository.findByRoleMnemonic(roleMnemonic);
    }

    public Boolean checkUserName(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    private String passwordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    private Boolean checkRole(String role) {
        return roleRepository.findByMnemonic(role).isPresent();
    }

    /**
     * Если пользователь на проект
     *
     * @param projectId
     * @param user
     * @return если имя проекта есть в системе, то создается пользователь. Если нет, то Optional.empty()
     */
    private Optional<User> userWithProject(Long projectId, User user) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            user.setProject(project.get());
            log.debug("User set project: " + user.getProject());
            return Optional.of(userRepository.save(user));
        } else {
            log.info("Project: " + projectId + "not found");
            return Optional.empty();
        }
    }
}
