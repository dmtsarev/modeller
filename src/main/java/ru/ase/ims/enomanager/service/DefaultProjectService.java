package ru.ase.ims.enomanager.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.ProjectStatus;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.repository.ProjectRepository;
import ru.ase.ims.enomanager.service.git.GitManager;
import ru.ase.ims.enomanager.service.git.Status;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GitManager gitManager;
    private final ReleaseManager releaseManager;

    public DefaultProjectService(ProjectRepository projectRepository,
                                 GitManager gitManager, ReleaseManager releaseManager) {
        this.projectRepository = projectRepository;
        this.gitManager = gitManager;
        this.releaseManager = releaseManager;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }
    
    @Override
    public Project createProject(Project project) {
        try {
            project.setStatus(ProjectStatus.PENDING);
            project.setId(null);
            project.getGitRepository().setId(null);
            Project persistProject = projectRepository.save(project);
            project.setId(persistProject.getId());
            project.setGitRepository(persistProject.getGitRepository());
            this.gitManager.addRepositoryEventListener(event -> {
                this.gitManager.addRepositoryEventListener(null);
                if(event.getStatus() == Status.SUCCESS) {

                    projectRepository.save(project);
                    try {
                        List<String> branchNameList = this.gitManager.getGitClient(project.getGitRepository().getId()).getLocalBranchList();
                        branchNameList.forEach(item -> releaseManager.createRelease(new Release(0L, "", item, item, item, project)));
                        project.setStatus(ProjectStatus.CREATED);
                        projectRepository.save(project);
                    } catch (GitAPIException e) {
                        e.printStackTrace();
                        project.setStatus(ProjectStatus.ERROR);
                        projectRepository.save(project);
                    }
                } else {
                    project.setStatus(ProjectStatus.ERROR);
                    projectRepository.save(project);
                }
            });
            gitManager.cloneRepository(project.getGitRepository());
        } catch (DataIntegrityViolationException exception) {
            throw new CreateReleaseException(exception.getMessage());
        } catch (Exception e) {
            project.setStatus(ProjectStatus.ERROR);
            projectRepository.save(project);
            e.printStackTrace();
        }
        return project;
    }
}
