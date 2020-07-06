package ru.ase.ims.enomanager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ase.ims.enomanager.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, String> {
    Optional<Role> findByMnemonic(String role);
}