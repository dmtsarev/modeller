package ru.ase.ims.enomanager.service.git;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.git.GitRepository;
import ru.ase.ims.enomanager.service.FileService;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(name = "application.git.type", havingValue = "GitLocal")
public class DefaultGitManager implements GitManager {

    private Git git;
    public static final String GIT_PATH = "gitExample";
    private Repository repository;
    private final FileService fileService;

    public DefaultGitManager(FileService fileService) {
        System.out.println("git local");
        this.fileService = fileService;
        try (Repository tmpRepository = new FileRepositoryBuilder().setGitDir(new File(GIT_PATH)).readEnvironment().findGitDir().build()) {
            this.repository = tmpRepository;
            git = new Git(repository);
        } catch (AccessDeniedException e) {
            log.info("AccessDeniedException: " + e.getMessage());
        } catch (Exception e) {
            log.info("FileRepository initial defaultGitManager with exception " + e);
            initGit();
        }
    }

    @Override
    public void cloneRepository(GitRepository gitRepository) throws IOException {

    }

    @Override
    public Ref createBranch(String branchName) throws CreateReleaseException, GitAPIException {
        try {
            git.branchCreate().setName(branchName).call();
            return git.checkout().setName(branchName).call();
        } catch (RefAlreadyExistsException e) {
            log.info("CreateBranch with RefAlreadyExistsException: " + e);
            throw new CreateReleaseException(e.getMessage());
        } catch (RefNotFoundException | InvalidRefNameException | CheckoutConflictException e) {
            log.info("CreateBranch with exception: " + e);
            new CreateReleaseException(e.getMessage());
        } catch (GitAPIException e) {
            log.info("CreateBranch with GitAPIException: " + e);
            throw e;
        }
        return null;
    }

    private void initGit() {
        try {
            log.info("Initial git repository to path: " + GIT_PATH);
            repository = FileRepositoryBuilder.create(new File(GIT_PATH, ".git"));
            repository.create();
            git = new Git(repository);
            log.info("Author for commit git: " + "system, system@system.test");
            git.commit().setAuthor("system", "system@system.test").setMessage("Initial commit").call();
        } catch (Exception e) {
            log.info("Git initial with exception: " + e);
        }
    }

    @Override
    public List<String> getFiles(String branchName) {
        log.debug("BranchName: " + branchName);
        try {
            git.checkout().setName(branchName).call();
            log.info("Success getFiles to branchName: " + branchName);
            return fileService.listFiles(GIT_PATH);
        } catch (UnsupportedOperationException e) {
            log.info("UnsupportedOperationException to getFiles" + e.getMessage());
            return fileService.listFiles(GIT_PATH);
        } catch (GitAPIException e) {
            log.info("GitAPIException to getFiles: " + e.getMessage());
        }
        return null;
    }

    @Override
    public InputStream getFileAsStream(String branchName, String fileName) {
        log.debug("GetFileAsStream to branchName: " + branchName + "fileName: " + fileName);
        try {
            git.checkout().setName(branchName).call();
        } catch (UnsupportedOperationException e) {
            log.info("UnsupportedOperationException to getFileAsStream to branchName: " + branchName +
                    " fileName: " + fileName +
                    " exception: " + e.getMessage());
        } catch (GitAPIException e) {
            log.info("GitAPIException to getFileAsStream to branchName: " + branchName +
                    " fileName: " + fileName +
                    " exception: " + e.getMessage());
        }
        try {
            return fileService.readFile(GIT_PATH + "/" + fileName);
        } catch (FileNotFoundException ex) {
            log.info("FileNotFoundException to getFileAsStream to branchName: " + branchName +
                    " fileName: " + fileName +
                    " exception: " + ex.getMessage());
        }
        return null;
    }

    //TODO
    @Override
    public GitClient getGitClient(Long gitRepositoryId) {
        return null;
    }

    @Override
    public boolean testConnection(GitRepository gitRepository) {
        return false;
    }

    @Override
    public void addRepositoryEventListener(RepositoryEventListener repositoryEventListener) {

    }
}
