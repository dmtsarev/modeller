package ru.ase.ims.enomanager.service.git;

import lombok.Getter;
import lombok.Setter;

public class GitRepositoryEvent {
    @Getter
    @Setter
    private long sourceId;
    @Getter
    @Setter
    private Status status;
    public GitRepositoryEvent(long sourceId, Status status) {
        this.sourceId = sourceId;
        this.status = status;
    }
}
