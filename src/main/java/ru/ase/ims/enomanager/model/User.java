package ru.ase.ims.enomanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "user_name", nullable = false, unique = true)
    @Size(max = 256)
    private String userName;

    @Getter
    @Setter
    @Size(max = 256)
    @NotNull
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_mnemonic", nullable = false)
    @Getter
    @Setter
    @NotNull
    private Role role;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Getter
    @Setter
    private Project project;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User(String userName, String password, Role role, Project project) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.project = project;
    }
}
