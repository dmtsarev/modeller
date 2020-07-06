package ru.ase.ims.enomanager.entity;

import lombok.Getter;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Project;
import ru.ase.ims.enomanager.model.Release;

import java.util.Set;

public class ReleasesBuilder {

    public static ReleasesBuilderEx getReleasesBuilder() {
        return new ReleasesBuilderEx();
    }

    public static class ReleasesBuilderEx {

        @Getter
        private Release release;

        ReleasesBuilderEx() {
            release = new Release();
        }

        public ReleasesBuilderEx id(Long id) {
            this.release.setId(id);
            return this;
        }

        public ReleasesBuilderEx description(String description) {
            this.release.setDescription(description);
            return this;
        }

        public ReleasesBuilderEx branchName(String branchName) {
            this.release.setBranchName(branchName);
            return this;
        }

        public ReleasesBuilderEx extReleaseId(String extReleaseId) {
            this.release.setExtReleaseId(extReleaseId);
            return this;
        }

        public ReleasesBuilderEx releaseName(String releaseName) {
            this.release.setReleaseName(releaseName);
            return this;
        }

        public ReleasesBuilderEx enoviaEntities(Set<EnoviaEntity> enoviaEntityes) {
            this.release.setEnoviaEntities(enoviaEntityes);
            return this;
        }
        public ReleasesBuilderEx project(Project project) {
            this.release.setProject(project);
            return this;
        }
    }
}
