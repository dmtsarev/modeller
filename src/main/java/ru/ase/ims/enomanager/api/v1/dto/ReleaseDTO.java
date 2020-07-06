package ru.ase.ims.enomanager.api.v1.dto;

import lombok.Data;

@Data
public class ReleaseDTO {
    private long releaseId;
    private String status;
    private String releaseName;
    private String description;
}
