package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.service.DefaultReleaseManager;
import ru.ase.ims.enomanager.service.DefaultTagService;
import ru.ase.ims.enomanager.service.EntityService;
import ru.ase.ims.enomanager.service.SearchByTagService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "${application.v1API}/search")
@Api(value = "/search", tags = {"Search By Tag Controller"})
@RequiredArgsConstructor
@Slf4j
public class DefaultSearchByTagController {
    private final SearchByTagService searchByTagService;
    private final DefaultTagService tagService;

    @ApiOperation(value = "Returns list of entities for specified tags", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Release not found"),
    })
    @GetMapping(path = "/entities", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EnoviaEntity> getEnoviaEntities(@RequestParam(name = "tags") Set<Long> tags) {
        return  searchByTagService.getEntityList(tags);
    }

    @ApiOperation(value = "Returns list of entities for specified tags and releases", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Release not found"),
    })
    @GetMapping(path = "/releases/entities", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EnoviaEntity> getEnoviaEntities(@RequestParam(name = "tags") Set<Long> tags,
                                                @RequestParam(name = "releases") Set<Long> releases) {
        return  searchByTagService.getEntityListByReleases(tags, releases);
    }

    @ApiOperation(value = "Returns list of tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
    })
    @GetMapping(path="/tags", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Tag>> tags() {
        List<Tag> tags = tagService.getTags();
        if(tags.isEmpty()) {
            log.debug("tags is empty");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().body(tags);
        }
    }

    @ApiOperation(value = "Returns list of id releases", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
    })
    @GetMapping(path="/releases", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Long> releases() {
        return searchByTagService.getReleaseList();
    }
}
