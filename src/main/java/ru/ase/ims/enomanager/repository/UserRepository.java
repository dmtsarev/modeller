package ru.ase.ims.enomanager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ase.ims.enomanager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String username);
    List<User> findByRoleMnemonic(String roleMnemonic);
    List<User> findByProjectId(Long id);
}

