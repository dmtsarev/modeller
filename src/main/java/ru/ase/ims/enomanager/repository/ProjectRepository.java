package ru.ase.ims.enomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ase.ims.enomanager.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
