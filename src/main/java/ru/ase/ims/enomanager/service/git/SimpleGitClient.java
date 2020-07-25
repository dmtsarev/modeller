package ru.ase.ims.enomanager.service.git;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.model.git.GitRepository;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class SimpleGitClient implements GitClient {
    private Git git;
    private Repository repository;
    private String username;
    private String password;
    private String uri;
    private String path;
    private long cloneProgress = 0L;
    private boolean cloneIsDone = false;
    private RepositoryEventListener repositoryEventListener;
    private long gitRepositoryId;

    public SimpleGitClient() {
    }

    @Override
    public SimpleGitClient init(GitRepository gitRepository) {
        this.username = gitRepository.getUsername();
        this.password = gitRepository.getPassword();
        this.uri = gitRepository.getUri();
        if (gitRepository.getId() != 0) {
            try {
                this.path = URLDecoder.decode(getClass().getClassLoader().getResource("").getPath() + "projects/" + gitRepository.getId(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.gitRepositoryId = gitRepository.getId();
        return this;
    }

    @Override
    public void openLocalRepository() throws IOException {
        synchronized (this) {
            this.git = Git.open(new File(this.path));
            this.repository = git.getRepository();
        }
    }

    @Override
    public TreeWalk getBranchTree() throws IOException {
        TreeWalk treeWalk = new TreeWalk(repository);
        Ref head = repository.getRefDatabase().findRef("HEAD");
        RevCommit commit = repository.parseCommit(head.getObjectId());
        treeWalk.addTree(commit.getTree());
        treeWalk.setRecursive(false);
        return treeWalk;
    }

    public void addRepositoryEventListener(RepositoryEventListener repositoryEventListener) {
        this.repositoryEventListener = repositoryEventListener;
    }

    @Override
    public boolean checkConnection() {
        try {
            Collection<Ref> refList = Git.lsRemoteRepository().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setRemote(this.uri).call();
            refList.forEach(System.out::println);
            return true;
        } catch (GitAPIException e) {
            return false;
        }
    }

    public void removeRepositoryEventListener() {
        this.repositoryEventListener = null;
    }
    @Async
    public void cloneRepository() throws GitAPIException{
        try {
            this.git = Git.cloneRepository().setURI(uri).setDirectory(new File(path))
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                    .setProgressMonitor(new SimpleProgressMonitor())
                    .call();
            repository = git.getRepository();
        } catch (Exception e) {
            repositoryEventListener.cloneCompleted(new GitRepositoryEvent(gitRepositoryId, Status.FAILED));
            throw e;
        }
        repositoryEventListener.cloneCompleted(new GitRepositoryEvent(gitRepositoryId, Status.SUCCESS));
    }

    @Override
    public List<String> getRemoteBranchList() throws GitAPIException {
        List<Ref> branchList = this.git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
        return branchList.stream().map(Ref::getName).collect(Collectors.toList());
    }
    @Override
    public List<String> getLocalBranchList() throws GitAPIException {
        List<Ref> branchList = this.git.branchList().call();
        return branchList.stream().map(Ref::getName).collect(Collectors.toList());
    }

    @Override
    public Repository getRepository() {
        return this.repository;
    }

    @Override
    public Ref checkoutBranch(String branchName) throws GitAPIException {
        return git.checkout().setName(branchName).call();
    }

    @Override
    public ArrayList<String> getNonExistentRemoteBranchList() throws GitAPIException {
        Collection<Ref> refList = Git.lsRemoteRepository().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setRemote(this.uri).call();
        List<String> branchList = getLocalBranchList();
        ArrayList<String> createBranchList = new ArrayList<>();
        for (Ref ref: refList){
            var name = ref.getName();
            boolean flag = true;
            if(!name.equals("HEAD")){
                for (String branch : branchList){
                    if(name.equals(branch)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    createBranchList.add(name);
                }
            }
        }
        return createBranchList;
    }

    @Override
    public Ref createRemoteBranch(String branchName) throws GitAPIException {
        return git.checkout().
                setCreateBranch(true).
                setName(branchName).
                setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
                setStartPoint("origin/" + branchName).
                call();
    }

    @Override
    public FetchResult fetchOrigin() throws GitAPIException {
        return git.fetch().setRemote("origin").call();
    }

    @Override
    public TreeWalk getBranchTreeByName(String branchName, String fileName) throws IOException {
        ObjectId ref = repository.resolve(branchName);
        RevWalk revWalk = new RevWalk(repository);
        RevCommit revCommit = revWalk.parseCommit(ref);
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(revCommit.getTree());
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(fileName));
        return treeWalk;
    }

    private class SimpleProgressMonitor implements ProgressMonitor {

        @Override
        public void start(int totalTasks) {

        }

        @Override
        public void beginTask(String title, int totalWork) {

        }

        @Override
        public void update(int completed) {
            cloneProgress += completed;
        }

        @Override
        public void endTask() {
            cloneIsDone = true;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }

    public long getCloneProgress() {
        return cloneProgress;
    }

    public boolean isCloneIsDone() {
        return cloneIsDone;
    }
}
