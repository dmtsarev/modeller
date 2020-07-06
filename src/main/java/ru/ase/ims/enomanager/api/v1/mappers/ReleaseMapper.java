package ru.ase.ims.enomanager.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ase.ims.enomanager.api.v1.dto.ReleaseDTO;
import ru.ase.ims.enomanager.model.Release;

@Mapper
public interface ReleaseMapper {
    ReleaseMapper RELEASE_MAPPER = Mappers.getMapper(ReleaseMapper.class);
    @Mapping(source = "id", target = "releaseId")
    @Mapping(target = "status", defaultValue = "NOT_CACHED")
    ReleaseDTO releaseToReleaseDTO(Release release);
}
