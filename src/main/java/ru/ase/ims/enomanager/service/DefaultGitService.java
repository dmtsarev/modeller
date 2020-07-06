package ru.ase.ims.enomanager.service;

import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.model.git.GitRepository;
import ru.ase.ims.enomanager.repository.GitRepositoryRepository;
import ru.ase.ims.enomanager.service.git.GitManager;

@Service
public class DefaultGitService implements GitService {

    private final GitManager gitManager;
    private final GitRepositoryRepository gitRepositoryRepository;

    public DefaultGitService(GitManager gitManager, GitRepositoryRepository gitRepositoryRepository) {
        this.gitManager = gitManager;
        this.gitRepositoryRepository = gitRepositoryRepository;
    }

    @Override
    public GitRepository saveGitRepository(GitRepository gitRepository) {
        return this.gitRepositoryRepository.save(gitRepository);

    }

    @Override
    public boolean checkGitRepository(GitRepository gitRepository) {
        return this.gitManager.testConnection(gitRepository);
    }
}
