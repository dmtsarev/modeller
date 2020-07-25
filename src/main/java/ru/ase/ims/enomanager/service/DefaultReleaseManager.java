package ru.ase.ims.enomanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.ProjectStatus;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.repository.ProjectRepository;
import ru.ase.ims.enomanager.repository.ReleaseRepository;
import ru.ase.ims.enomanager.service.git.GitClient;
import ru.ase.ims.enomanager.service.git.GitManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultReleaseManager implements ReleaseManager {

    private final ReleaseRepository releaseRepository;
    private final GitManager gitManager;
    private final EntityService entityService;
    private final ProjectRepository projectRepository;

    @Override
    public Release createRelease(Release release) {
        if (release == null) return null;
        log.debug("Release id: " + release.getId() +
                   " releaseName: " + release.getReleaseName() +
                   " releaseBranchName: " + release.getBranchName());
        try {
            gitManager.createBranch(release.getBranchName());
            log.info("Success create branch: " + release.getBranchName());
            release = releaseRepository.save(release);
            this.getRelease(release.getId());
            return release;
        } catch (GitAPIException e) {
            log.info("GitAPIException for branchName: " + release.getBranchName());
        }
        return null;
    }

    @Override
    public Optional<Release> getRelease(Long releaseId) {
        return releaseRepository.findById(releaseId).map(release -> {
            entityService.findEntities(release);
            return Optional.of(release);
        }).orElse(Optional.empty());
    }

    @Override
    public List<Release> getAllReleases() {
        return releaseRepository.findAll();
    }

    @Override
    public List<Release> getAllReleases(Long projectId) {
        createReleasesIfAny(projectId);
        return releaseRepository.findAllByProjectId(projectId);
    }

    private void createReleasesIfAny(Long projectId){
        this.projectRepository.findById(projectId).map(project -> {
            try {
                synchronized (GitClient.class){
                    var gitClient = this.gitManager.getGitClient(project.getGitRepository().getId());
                    var remoteBranchList = gitClient.getNonExistentRemoteBranchList();
                    if (!remoteBranchList.isEmpty()){
                        gitClient.fetchOrigin();
                        for (String remoteBranch : remoteBranchList){
                            String nameRemoteBranch = remoteBranch.substring(11);
                            gitClient.createRemoteBranch(nameRemoteBranch);
                        }
                        List<String> localBranchList = gitClient.getLocalBranchList();
                        for (String localBranch : localBranchList){
                            if(releaseRepository.findByBranchName(localBranch).isEmpty()){
                                createRelease(new Release(0L, "", localBranch, localBranch, localBranch, project));
                                project.setStatus(ProjectStatus.CREATED);
                                projectRepository.save(project);
                            }
                        }
                    }
                }
            } catch (GitAPIException e) {
                e.printStackTrace();
                project.setStatus(ProjectStatus.ERROR);
                projectRepository.save(project);
            }
            return null;
        });
    }

}
