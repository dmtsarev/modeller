package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> createUser(String userName, String password, String roleMnemonic);
    Optional<User> createUser(String userName, String password, String roleMnemonic, Long projectId);
    Optional<User> updateUserRole(Long id, String roleMnemonic);
    Optional<User> getUser(Long id);
    void delete(Long id);
    List<User> getUsers();
    List<User> getUsersOnRole(String roleMnemonic);
}
