package ru.ase.ims.enomanager.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.api.v1.dto.GitRepositoryDTO;
import ru.ase.ims.enomanager.model.git.GitRepository;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.0.Beta3, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class GitRepositoryMapperImpl implements GitRepositoryMapper {

    @Override
    public GitRepositoryDTO repositoryToRepositoryDTO(GitRepository gitRepository) {
        if ( gitRepository == null ) {
            return null;
        }

        GitRepositoryDTO gitRepositoryDTO = new GitRepositoryDTO();

        if ( gitRepository.getId() != null ) {
            gitRepositoryDTO.setRepositoryId( gitRepository.getId() );
        }
        gitRepositoryDTO.setUri( gitRepository.getUri() );

        return gitRepositoryDTO;
    }

    @Override
    public GitRepository repositoryDTOToRepository(GitRepositoryDTO gitRepositoryDTO) {
        if ( gitRepositoryDTO == null ) {
            return null;
        }

        GitRepository gitRepository = new GitRepository();

        gitRepository.setPassword( gitRepositoryDTO.getPassword() );
        gitRepository.setUsername( gitRepositoryDTO.getUsername() );
        gitRepository.setId( gitRepositoryDTO.getRepositoryId() );
        gitRepository.setUri( gitRepositoryDTO.getUri() );

        return gitRepository;
    }
}
