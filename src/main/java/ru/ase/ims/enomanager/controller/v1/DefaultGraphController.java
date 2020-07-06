package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.service.ReleaseManager;
import ru.ase.ims.enomanager.service.graph.GraphService;

@RestController
@RequestMapping(path = "${application.v1API}/graph")
@Api(value = "/graph", tags = {"Graph Controller"})
@RequiredArgsConstructor
public class DefaultGraphController {

    private final GraphService graphService;
    private final ReleaseManager releaseManager;

    /*@GetMapping(
            path = "/{releaseId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public Map<String, Vertex> getGraph(@PathVariable Long releaseId) {
        return releaseManager.getRelease(releaseId).map(r -> graphService.getGraph(r.getBranchName())).orElse(Collections.emptyMap());
    }*/
}
