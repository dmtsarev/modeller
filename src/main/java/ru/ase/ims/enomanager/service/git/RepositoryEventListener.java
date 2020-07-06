package ru.ase.ims.enomanager.service.git;

public interface RepositoryEventListener {
    void cloneCompleted(GitRepositoryEvent event);
}
