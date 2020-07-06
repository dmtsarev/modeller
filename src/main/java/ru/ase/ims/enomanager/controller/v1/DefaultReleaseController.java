package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.api.v1.dto.ReleaseDTO;
import ru.ase.ims.enomanager.api.v1.mappers.ReleaseMapper;
import ru.ase.ims.enomanager.model.CacheStatus;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.service.ReleaseManager;
import ru.ase.ims.enomanager.service.graph.GraphService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "${application.v1API}/releases")
@Api(value = "/releases", tags = {"Release Controller"})
/*@SwaggerDefinition(tags = {
        @Tag(name = "Release Controller", description = "Create and load releases")
})*/
@RequiredArgsConstructor
public class DefaultReleaseController {

    private final ReleaseManager releaseManager;
    private final GraphService graphService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Creates release entity, creates corresponding branch in git",
            response = Release.class,
            authorizations = {@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @PreAuthorize("hasRole('BUSINESS_ADMIN') OR hasAuthority('ADMIN')")
    public Release createRelease(@RequestBody  Release release) {
        return releaseManager.createRelease(release);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Loads releases list and cached releases names",
            response = Release.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @Transactional
    public List<ReleaseDTO> getReleasesAndCacheStatus(@RequestParam(value = "projectId", required = false) Long projectId) {
        ReleaseMapper mapper = ReleaseMapper.RELEASE_MAPPER;
        List<ReleaseDTO> releaseDTOList = new ArrayList<>();
        if(projectId != null) {
            releaseDTOList = releaseManager.getAllReleases(projectId).stream()
                    .map(mapper::releaseToReleaseDTO).collect(Collectors.toList());
        } else {
            releaseDTOList = releaseManager.getAllReleases().stream()
                    .map(mapper::releaseToReleaseDTO).collect(Collectors.toList());
        }
        var cachedIndexes = graphService.getCachedGraphs();
        releaseDTOList.forEach(item -> {
            item.setStatus(CacheStatus.NOT_CACHED.toString());
            cachedIndexes.forEach(index -> {
                if(index.longValue() == item.getReleaseId()) {
                    item.setStatus(CacheStatus.CACHED.toString());
                }
            });
        });
        return releaseDTOList;
    }

    @GetMapping (
            path = "/{releaseId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Load release and cache all data",
            response = Release.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Release cached"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @Transactional
    public ResponseEntity<Release> getRelease(@PathVariable Long releaseId) {
        return ResponseEntity.ok(releaseManager.getRelease(releaseId).get());
    }
}
