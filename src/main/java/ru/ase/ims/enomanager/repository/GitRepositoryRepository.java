package ru.ase.ims.enomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GitRepositoryRepository extends JpaRepository<ru.ase.ims.enomanager.model.git.GitRepository, Long> {
}
