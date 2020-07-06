package ru.ase.ims.enomanager.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import ru.ase.ims.enomanager.entity.ReleasesBuilder;
import ru.ase.ims.enomanager.model.Release;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@Disabled
public class DefaultGraphControllerTest extends AbstractSpringControllerTest {

    @Before
    public void addDataToDB() {

        Release release = ReleasesBuilder.getReleasesBuilder()
                .branchName("TestBranchName")
                .releaseName("TestReleaseName")
                .extReleaseId("TestExtReleasesName")
                .description("TestDescription")
                .getRelease();

        //repository.releaseRepository.save(release);
    }

    @After
    public void clearDB() {
        repository.releaseRepository.deleteAll();
        repository.entityRepository.deleteAll();
    }

    @Test
    public void getGraph() {

        Optional<Release> release = repository.releaseRepository.findAll().stream().findFirst();
        //assertTrue(release.isPresent());
        //todo add test later
        //controller.defaultGraphController.getGraph(release.get().getId());
    }

}
