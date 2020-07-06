package ru.ase.ims.enomanager.service.git;

import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.git.GitRepository;
import ru.ase.ims.enomanager.service.RepositoryEventEmmiter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface GitManager extends RepositoryEventEmmiter {

    void cloneRepository(GitRepository gitRepository) throws IOException, GitAPIException;
    Ref createBranch(String branchName) throws CreateReleaseException, GitAPIException;
    List<String> getFiles(String branchName);
    InputStream getFileAsStream(String branchName,String  fileName);
    GitClient getGitClient(Long gitRepositoryId);
    boolean testConnection(GitRepository gitRepository);
}
