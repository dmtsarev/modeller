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
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.graph.EntityGraph;
import ru.ase.ims.enomanager.service.EntityService;
import ru.ase.ims.enomanager.service.DefaultRoleEntityService;
import ru.ase.ims.enomanager.service.RoleEntityService;
import ru.ase.ims.enomanager.service.xml.XMLReader;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "${application.v1API}/entity")
@Api(value = "/entity", tags = {"Entity Controller"})
@RequiredArgsConstructor
@Slf4j
public class DefaultEntityController {

    private static final String HTML_TYPE = "html";
    private static final String JSON_TYPE = "json";
    private final EntityService entityService;
    private final RoleEntityService roleEntityService;
    private final XMLReader xmlReader;

    @ApiOperation(value = "Returns list of entities for specified release", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Release not found"),
    })
    @GetMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EnoviaEntity> getEnoviaEntities(@RequestParam(name = "releaseId") Long releaseId,
                                                @RequestParam(name = "type", required = false) String type,
                                                @RequestParam(name = "searchWord", required = false) String searchWord) {
        return  entityService.getEntityList(releaseId, type, searchWord);
    }

    @ApiOperation(value = "Returns tree of role entities for specified release", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Release not found"),
    })
    @GetMapping(path = "/roles", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity getEnoviaRoleEntities(@RequestParam(name = "releaseId") Long releaseId,
                                                @RequestParam(name = "searchWord", required = false) String searchWord) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(roleEntityService.getRoleEntityTree(releaseId, searchWord));
    }

    @ApiOperation(value = "Updates entity", response = EnoviaEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Entity not found"),
    })
    @PutMapping(path = "{entityId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public EnoviaEntity updateEntity(@PathVariable(name = "entityId") Long entityId,
                                       @RequestBody EnoviaEntity enoviaEntityNew) {
        return entityService.updateEntity(entityId, enoviaEntityNew);
    }

    @ApiOperation(value = "Returns Enovia entity", response = Release.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Entity not found")
    })
    @GetMapping(value = "/{entityId}", produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity getEntity(@PathVariable(name = "entityId") Long entityId,
                                    @RequestParam(name = "content", required = false, defaultValue = JSON_TYPE) String contentType) {
        EnoviaEntity enoviaEntity = entityService.getEntity(entityId);
        switch (contentType) {
            case HTML_TYPE:
                return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(xmlReader.getHtml(enoviaEntity.getEmatrix()));
            default:
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(enoviaEntity);
        }
    }

    @GetMapping(value = "/{entityId}/graph")
    @Transactional
    public ResponseEntity<EntityGraph> getEntityGraph(@PathVariable(name = "entityId") Long entityId) {
        EntityGraph entityGraph = entityService.getEntityGraph(entityId);
        if(entityGraph == null) {
            log.info("EntityService getEntityGraph is null");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(entityGraph);
        }
    }

    /*@GetMapping(value = "{entityName}", produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getEntityByName(@PathVariable(name = "entityName") String entityName,
                                    @RequestParam(name = "releaseId", required = false) Long releaseId,
                                    @RequestParam(name = "content", required = false, defaultValue = JSON_TYPE) String contentType) {
        switch(contentType) {
            case HTML_TYPE: return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
                    .body(xmlReader.getHtml(entityService.getEntity(entityName, releaseId).getEmatrix()));
            default: return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(entityService.getEntity(entityName, releaseId));
        }
    }*/
}
