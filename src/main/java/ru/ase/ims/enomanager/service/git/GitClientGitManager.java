package ru.ase.ims.enomanager.service.git;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.git.GitRepository;
import ru.ase.ims.enomanager.repository.GitRepositoryRepository;
import ru.ase.ims.enomanager.repository.ProjectRepository;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("GitClient")
@ConditionalOnProperty(name = "application.git.type", havingValue = "GitClient")
public class GitClientGitManager implements GitManager {

    private final ProjectRepository projectRepository;
    private final GitRepositoryRepository gitRepositoryRepository;
    final Map<Long, GitClient> gitClientMap = new HashMap<>();
    private RepositoryEventListener repositoryEventListener;

    public GitClientGitManager(ProjectRepository projectRepository, GitRepositoryRepository gitRepositoryRepository) {
        this.projectRepository = projectRepository;
        this.gitRepositoryRepository = gitRepositoryRepository;
    }

    @Lookup
    public GitClient getGitClient() {
        return null;
    }

    @PostConstruct
    private void init() {
        List<Project> projectList = projectRepository.findAll();
        projectList.parallelStream().forEach(project -> {
            try {
                GitClient gitClient = getGitClient();
                gitClient.init(project.getGitRepository());
                gitClient.openLocalRepository();
                this.gitClientMap.put(project.getGitRepository().getId(), gitClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //TODO project status
    @Override
    public void cloneRepository(GitRepository gitRepository) throws GitAPIException {
        GitClient gitClient = getGitClient();
        this.gitClientMap.put(gitRepository.getId(), gitClient);
        GitRepository tmp = gitRepositoryRepository.getOne(gitRepository.getId());
        gitClient.init(tmp);
        gitClient.addRepositoryEventListener(event -> {
            if (event.getStatus() == Status.SUCCESS) {
                gitRepositoryRepository.save(gitRepository);
                repositoryEventListener.cloneCompleted(new GitRepositoryEvent(gitRepository.getId(), Status.SUCCESS));
            } else {
                repositoryEventListener.cloneCompleted(new GitRepositoryEvent(gitRepository.getId(), Status.FAILED));
            }
        });
        gitClient.cloneRepository();
    }

    @Override
    public Ref createBranch(String branchName) throws CreateReleaseException, GitAPIException {
        return null;
    }

    @Override
    public List<String> getFiles(String branchName) {
        return null;
    }

    @Override
    public InputStream getFileAsStream(String branchName, String fileName) {
        return null;
    }

    @Override
    public GitClient getGitClient(Long gitRepositoryId) {
        return gitClientMap.get(gitRepositoryId);
    }

    @Override
    public boolean testConnection(GitRepository gitRepository) {
        try {
            return this.getGitClient().init(gitRepository).checkConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addRepositoryEventListener(RepositoryEventListener repositoryEventListener) {
        this.repositoryEventListener = repositoryEventListener;
    }
}
