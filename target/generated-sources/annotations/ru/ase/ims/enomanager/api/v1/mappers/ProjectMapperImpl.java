package ru.ase.ims.enomanager.api.v1.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.ase.ims.enomanager.api.v1.dto.GitRepositoryDTO;
import ru.ase.ims.enomanager.api.v1.dto.ProjectDTO;
import ru.ase.ims.enomanager.api.v1.dto.ReleaseDTO;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.ProjectStatus;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.git.GitRepository;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.0.Beta3, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectDTO projectToProjectDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        if ( project.getId() != null ) {
            projectDTO.setProjectId( project.getId() );
        }
        projectDTO.setReleases( releaseSetToReleaseDTOList( project.getReleases() ) );
        projectDTO.setGitPath( projectGitRepositoryUri( project ) );
        Long id = projectGitRepositoryId( project );
        if ( id != null ) {
            projectDTO.setGitConnectionId( id );
        }
        projectDTO.setProjectName( project.getProjectName() );
        projectDTO.setDescription( project.getDescription() );
        if ( project.getStatus() != null ) {
            projectDTO.setStatus( project.getStatus().name() );
        }

        return projectDTO;
    }

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

    @Override
    public Project projectDTOToProject(ProjectDTO projectDTO) {
        if ( projectDTO == null ) {
            return null;
        }

        Project project = new Project();

        if ( projectDTO.getGit() != null ) {
            if ( project.getGitRepository() == null ) {
                project.setGitRepository( new GitRepository() );
            }
            gitRepositoryDTOToGitRepository( projectDTO.getGit(), project.getGitRepository() );
        }
        if ( project.getGitRepository() == null ) {
            project.setGitRepository( new GitRepository() );
        }
        projectDTOToGitRepository( projectDTO, project.getGitRepository() );
        project.setId( projectDTO.getProjectId() );
        project.setReleases( releaseDTOListToReleaseSet( projectDTO.getReleases() ) );
        project.setProjectName( projectDTO.getProjectName() );
        project.setDescription( projectDTO.getDescription() );
        if ( projectDTO.getStatus() != null ) {
            project.setStatus( Enum.valueOf( ProjectStatus.class, projectDTO.getStatus() ) );
        }

        return project;
    }

    protected List<ReleaseDTO> releaseSetToReleaseDTOList(Set<Release> set) {
        if ( set == null ) {
            return null;
        }

        List<ReleaseDTO> list = new ArrayList<ReleaseDTO>( set.size() );
        for ( Release release : set ) {
            list.add( releaseToReleaseDTO( release ) );
        }

        return list;
    }

    private String projectGitRepositoryUri(Project project) {
        if ( project == null ) {
            return null;
        }
        GitRepository gitRepository = project.getGitRepository();
        if ( gitRepository == null ) {
            return null;
        }
        String uri = gitRepository.getUri();
        if ( uri == null ) {
            return null;
        }
        return uri;
    }

    private Long projectGitRepositoryId(Project project) {
        if ( project == null ) {
            return null;
        }
        GitRepository gitRepository = project.getGitRepository();
        if ( gitRepository == null ) {
            return null;
        }
        Long id = gitRepository.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected void gitRepositoryDTOToGitRepository(GitRepositoryDTO gitRepositoryDTO, GitRepository mappingTarget) {
        if ( gitRepositoryDTO == null ) {
            return;
        }

        mappingTarget.setUri( gitRepositoryDTO.getUri() );
        mappingTarget.setUsername( gitRepositoryDTO.getUsername() );
        mappingTarget.setPassword( gitRepositoryDTO.getPassword() );
    }

    protected void projectDTOToGitRepository(ProjectDTO projectDTO, GitRepository mappingTarget) {
        if ( projectDTO == null ) {
            return;
        }

        mappingTarget.setUri( projectDTO.getGitPath() );
        mappingTarget.setId( projectDTO.getGitConnectionId() );
    }

    protected Release releaseDTOToRelease(ReleaseDTO releaseDTO) {
        if ( releaseDTO == null ) {
            return null;
        }

        Release release = new Release();

        release.setDescription( releaseDTO.getDescription() );
        release.setReleaseName( releaseDTO.getReleaseName() );

        return release;
    }

    protected Set<Release> releaseDTOListToReleaseSet(List<ReleaseDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Release> set = new HashSet<Release>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( ReleaseDTO releaseDTO : list ) {
            set.add( releaseDTOToRelease( releaseDTO ) );
        }

        return set;
    }
}
