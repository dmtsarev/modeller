package ru.ase.ims.enomanager.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.api.v1.dto.ReleaseDTO;
import ru.ase.ims.enomanager.model.Release;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.0.Beta3, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class ReleaseMapperImpl implements ReleaseMapper {

    @Override
    public ReleaseDTO releaseToReleaseDTO(Release release) {
        if ( release == null ) {
            return null;
        }

        ReleaseDTO releaseDTO = new ReleaseDTO();

        if ( release.getId() != null ) {
            releaseDTO.setReleaseId( release.getId() );
        }
        releaseDTO.setReleaseName( release.getReleaseName() );
        releaseDTO.setDescription( release.getDescription() );

        return releaseDTO;
    }
}
