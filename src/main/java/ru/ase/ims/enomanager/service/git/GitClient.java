package ru.ase.ims.enomanager.service.git;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import ru.ase.ims.enomanager.model.git.GitRepository;
import ru.ase.ims.enomanager.service.RepositoryEventEmmiter;

import java.io.IOException;
import java.util.List;

public interface GitClient extends RepositoryEventEmmiter {
    void cloneRepository() throws GitAPIException;
    List<String> getRemoteBranchList() throws GitAPIException;
    List<String> getLocalBranchList() throws GitAPIException;
    Repository getRepository();
    Ref checkoutBranch(String branchName) throws GitAPIException;
    void addRepositoryEventListener(RepositoryEventListener repositoryEventListener);
    boolean checkConnection() throws GitAPIException;
    SimpleGitClient init(GitRepository gitRepository);

    void openLocalRepository() throws IOException;

    TreeWalk getBranchTree() throws IOException;
}
