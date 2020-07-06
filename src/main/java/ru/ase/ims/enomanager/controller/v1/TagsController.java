package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.model.TagType;
import ru.ase.ims.enomanager.service.DefaultTagService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Api(value = "/tools/tags", tags = {"Tags Controller"})
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "${application.v1API}/tools/tags")
public class TagsController {

    private final DefaultTagService tagService;

    /** ________________ Get _______________________ */

    @ApiOperation(value = "Returns list of tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Tag>> tags() {
        List<Tag> tags = tagService.getTags();
        if(tags.isEmpty()) {
            log.debug("tags is empty");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().body(tags);
        }
    }

    @ApiOperation(value = "Returns tags to name", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Tags empty"),
    })
    @GetMapping(path = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> tagByName(@PathVariable(name = "name") String name) {
        return tagService.getTag(name).map(e -> ResponseEntity.ok().body(e)).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Returns list of type", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Tags type empty"),
    })
    @GetMapping(path = "/types", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TagType>> tagsType() {
        List<TagType> tags = tagService.getTagTypes();
        return tags.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(tags);
    }

    @ApiOperation(value = "Returns list of tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
    })
    @GetMapping(path = "/types/{type}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Tag>> tagsByType(@PathVariable(name = "type") String type) {
        List<Tag> tags = tagService.getTagsByType(type);
        if(tags.isEmpty()) {
            log.debug("tags empty");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().body(tags);
        }
    }

    /** ________________ Post _______________________ */

    @ApiOperation(value = "Create tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success created"),
            @ApiResponse(code = 400, message = "Any error"),
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> createTags(@RequestBody Tag tag) {
        return tagService.createTag(tag).map(e -> ResponseEntity.status(HttpStatus.CREATED).body(e)).orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation(value = "Create tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success created"),
            @ApiResponse(code = 400, message = "Any error"),
    })
    @PostMapping(path="/types", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagType> createTagsType(@RequestBody TagType tagtype) {
        return tagService.createTagType(tagtype).map(e -> ResponseEntity.status(HttpStatus.CREATED).body(e)).orElse(ResponseEntity.badRequest().build());
    }


    /** ________________ Put _______________________ */

    @ApiOperation(value = "Update tags", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error"),
    })
    @PutMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tag> updateTags(@RequestBody Tag tag) {
        return tagService.updateTag(tag).map(e -> ResponseEntity.ok().body(e)).orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation(value = "Update tags type", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error"),
    })
    @PutMapping(path="/types/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagType> updateTagsType(@RequestBody TagType tagType) {
        return tagService.updateTagType(tagType).map(e -> ResponseEntity.ok().body(e)).orElse(ResponseEntity.badRequest().build());
    }

    /** ________________ Delete _______________________ */

    @ApiOperation(value = "Delete tags")
    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable("id") Long id) {
        log.info("Delete tags on id: " + id);
        tagService.deleteTag(id);
    }

    @ApiOperation(value = "Delete all tags")
    @DeleteMapping
    public void deleteAllTag() {
        log.info("Delete all tags");
        tagService.deleteAllTags();
    }

    @ApiOperation(value = "Delete tags Type")
    @DeleteMapping("/types/{name}")
    @Transactional
    public void deleteTagTypeByType(@PathVariable("name") String name) {
        log.info("Delete tags Type on name: " + name);
        tagService.deleteTagType(name);
    }
}
