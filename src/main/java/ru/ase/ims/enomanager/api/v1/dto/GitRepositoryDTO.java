package ru.ase.ims.enomanager.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GitRepositoryDTO {
    private long repositoryId;
    private String uri;
    private String username;
    private String password;
}
