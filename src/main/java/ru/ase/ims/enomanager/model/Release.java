package ru.ase.ims.enomanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "releases")
@NoArgsConstructor
public class Release {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "releases_sequence", sequenceName = "releases_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "releases_sequence")
    @Getter
    @Setter
    private Long id;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "branch_name")
    @Size(max = 256)
    @NotNull
    @Getter
    @Setter
    private String branchName;

    @Column(name = "ext_release_id")
    @Size(max = 256)
    @NotNull
    @Getter
    @Setter
    private String extReleaseId;

    @Column(name = "release_name")
    @Size(max = 256)
    @NotNull
    @Getter
    @Setter
    private String releaseName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Getter
    @Setter
    @NotNull
    @JsonBackReference
    private Project project;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonIgnore
    private Set<EnoviaEntity> enoviaEntities = new HashSet<>();

    public Release(Long id, String description, String branchName, String extReleaseId, String releaseName, Project project) {
        this.id = id;
        this.description = description;
        this.branchName = branchName;
        this.releaseName = releaseName;
        this.extReleaseId = extReleaseId;
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Release release = (Release) o;
        return Objects.equals(id, release.id) &&
                Objects.equals(description, release.description) &&
                Objects.equals(branchName, release.branchName) &&
                Objects.equals(extReleaseId, release.extReleaseId) &&
                Objects.equals(releaseName, release.releaseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, branchName, extReleaseId, releaseName);
    }
}
