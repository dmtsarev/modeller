package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.git.GitRepository;

public interface GitService {
    GitRepository saveGitRepository(GitRepository gitRepository);
    boolean checkGitRepository(GitRepository gitRepository);
}
