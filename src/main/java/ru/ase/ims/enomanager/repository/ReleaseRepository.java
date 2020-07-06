package ru.ase.ims.enomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ase.ims.enomanager.model.Release;

import java.util.List;
import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    Optional<Release> findByBranchName(String branchName);
    List<Release> findAllByProjectId(Long projectId);
}
