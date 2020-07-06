package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.Release;
import java.util.List;
import java.util.Optional;

public interface ReleaseManager {
    Release createRelease(Release release);
    Optional<Release> getRelease(Long releaseId);
    List<Release> getAllReleases();
    List<Release> getAllReleases(Long projectId);
}
