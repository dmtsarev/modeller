package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.service.git.RepositoryEventListener;

public interface RepositoryEventEmmiter {
    void addRepositoryEventListener(RepositoryEventListener repositoryEventListener);
}
