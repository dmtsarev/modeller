package ru.ase.ims.enomanager.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.*;
import ru.ase.ims.enomanager.repository.EntityRepository;
import ru.ase.ims.enomanager.repository.ProjectRepository;
import ru.ase.ims.enomanager.repository.ReleaseRepository;
import ru.ase.ims.enomanager.repository.TagsRepository;
import ru.ase.ims.enomanager.service.git.GitManager;
import ru.ase.ims.enomanager.service.git.Status;

import java.util.*;

@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GitManager gitManager;
    private final ReleaseManager releaseManager;
    private final ReleaseRepository releaseRepository;
    private final EntityRepository entityRepository;
    private final TagsRepository tagsRepository;

    public DefaultProjectService(ProjectRepository projectRepository,
                                 GitManager gitManager, ReleaseManager releaseManager, ReleaseRepository releaseRepository, EntityRepository entityRepository, TagsRepository tagsRepository) {
        this.projectRepository = projectRepository;
        this.gitManager = gitManager;
        this.releaseManager = releaseManager;
        this.releaseRepository = releaseRepository;
        this.entityRepository = entityRepository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        createReleasesIfAny(projectId);
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

    private void createReleasesIfAny(Long projectId){
        Project project = projectRepository.findById(projectId).orElse(null);
        try {
            var gitClient = this.gitManager.getGitClient(project.getGitRepository().getId());
            var remoteBranchList = gitClient.getNonExistentRemoteBranchList();
            if (!remoteBranchList.isEmpty()){
                Release releaseMax = this.releaseRepository.findFirstByOrderByIdDesc();
                gitClient.fetchOrigin();
                for (String remoteBranch : remoteBranchList){
                    String nameRemoteBranch = remoteBranch.substring(11);
                    gitClient.createRemoteBranch(nameRemoteBranch);
                }
                List<String> localBranchList = gitClient.getLocalBranchList();
                for (String localBranch : localBranchList){
                    if(releaseRepository.findByBranchName(localBranch).isEmpty()){
                        Release newRelease = this.releaseManager.createRelease(new Release(0L, "", localBranch, localBranch, localBranch, project));
                        project.setStatus(ProjectStatus.CREATED);
                        projectRepository.save(project);
                        copyingTags(releaseMax, newRelease);
                    }
                }
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
            project.setStatus(ProjectStatus.ERROR);
            projectRepository.save(project);
        }
    }

    private void copyingTags(Release inFrontOfRelease, Release behindRelease){
        for (EnoviaEntity inFrontOfEntity : entityRepository.findAllByReleaseId(inFrontOfRelease.getId())){
            for (EnoviaEntity behindEntity : entityRepository.findAllByReleaseId(behindRelease.getId())){
                if(!inFrontOfEntity.getTags().isEmpty()){
                    if (inFrontOfEntity.getFileName().equals(behindEntity.getFileName())){
                        Set<Tag> newTags = new HashSet<>();
                        for (Tag tag : inFrontOfEntity.getTags()){
                            newTags.add(new Tag(tag.getId(), tag.getName(), tag.getDescription(), tag.getType()));
                        }
                        behindEntity.setTags(newTags);
                        entityRepository.save(behindEntity);
                        break;
                    }
                }
            }
        }
    }

}
