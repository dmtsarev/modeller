package ru.ase.ims.enomanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ase.ims.enomanager.model.git.GitRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    @SequenceGenerator(name = "projects_sequence", sequenceName = "projects_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "projects_sequence")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "project_name")
    private String projectName;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonManagedReference
    @Column(name = "releases")
    private Set<Release> releases;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "git_id")
    @Getter
    @Setter
    private GitRepository gitRepository;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter
    @Setter
    private ProjectStatus status;
}
