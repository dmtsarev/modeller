package ru.ase.ims.enomanager.model.git;

import lombok.Data;
import ru.ase.ims.enomanager.model.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "git_repositories")
@Data
public class GitRepository {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "git_repositories_sequence", sequenceName = "git_repositories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "git_repositories_sequence")
    private Long id;
    @Column(name = "uri")
    @NotNull
    private String uri;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "gitRepository")
    private Project project;
}
