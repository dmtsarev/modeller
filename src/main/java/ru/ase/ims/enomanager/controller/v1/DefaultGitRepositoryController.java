package ru.ase.ims.enomanager.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ase.ims.enomanager.api.v1.dto.GitRepositoryDTO;
import ru.ase.ims.enomanager.api.v1.mappers.GitRepositoryMapper;
import ru.ase.ims.enomanager.service.GitService;

@RestController
@RequestMapping(path = "${application.v1API}/repository")
public class DefaultGitRepositoryController {
    private final GitService gitService;
    public DefaultGitRepositoryController(GitService gitService) {
        this.gitService = gitService;
    }

    @PostMapping
    public GitRepositoryDTO createGitRepository(@RequestBody GitRepositoryDTO gitRepositoryDTO) {
        return GitRepositoryMapper.GIT_REPOSITORY_MAPPER_MAPPER.repositoryToRepositoryDTO(
                gitService.saveGitRepository(GitRepositoryMapper.GIT_REPOSITORY_MAPPER_MAPPER.repositoryDTOToRepository(gitRepositoryDTO))
        );
    }
    @PostMapping(path = "/check")
    public ResponseEntity checkGitRepository(@RequestBody GitRepositoryDTO gitRepositoryDTO) {
        return gitService.checkGitRepository(GitRepositoryMapper.GIT_REPOSITORY_MAPPER_MAPPER.
                repositoryDTOToRepository(gitRepositoryDTO))? ResponseEntity.ok().build(): ResponseEntity.badRequest().build();
    }
}
