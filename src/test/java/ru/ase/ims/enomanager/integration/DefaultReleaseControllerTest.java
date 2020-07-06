package ru.ase.ims.enomanager.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.security.test.context.support.WithMockUser;
import ru.ase.ims.enomanager.entity.ReleasesBuilder;
import ru.ase.ims.enomanager.model.Release;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

//todo реализовать тесты
@Disabled
public class DefaultReleaseControllerTest extends AbstractSpringControllerTest {

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
    @WithMockUser(username="admin", authorities = {"ADMIN"})
    public void createRelease(){
        Optional<Release> release = repository.releaseRepository.findAll().stream().findFirst();
        //assertTrue(release.isPresent());

        //controller.defaultReleaseController.createRelease(release.get());
    }

    @Test
    public void getReleasesAndCacheStatus(){
        controller.defaultReleaseController.getReleasesAndCacheStatus(null);
    }

    @Test
    public void getRelease(){
        Optional<Release> release = repository.releaseRepository.findAll().stream().findFirst();
        //assertTrue(release.isPresent());

        //controller.defaultReleaseController.getRelease(release.get().getId());
    }
}
