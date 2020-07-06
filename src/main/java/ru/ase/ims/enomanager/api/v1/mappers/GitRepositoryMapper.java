package ru.ase.ims.enomanager.api.v1.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ru.ase.ims.enomanager.api.v1.dto.GitRepositoryDTO;
import ru.ase.ims.enomanager.model.git.GitRepository;


@Mapper
public interface GitRepositoryMapper {
    GitRepositoryMapper GIT_REPOSITORY_MAPPER_MAPPER = Mappers.getMapper(GitRepositoryMapper.class);
    @Mapping(source = "id", target = "repositoryId")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", ignore = true)
    GitRepositoryDTO repositoryToRepositoryDTO(GitRepository gitRepository);
    @InheritInverseConfiguration
    @Mapping(source = "password", target = "password")
    @Mapping(source = "username", target = "username")
    GitRepository repositoryDTOToRepository(GitRepositoryDTO gitRepositoryDTO);
}
