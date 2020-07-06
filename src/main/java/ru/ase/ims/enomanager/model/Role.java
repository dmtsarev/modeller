package ru.ase.ims.enomanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "mnemonic")
    @NotNull
    @Size(max = 256)
    @Getter
    @Setter
    private String mnemonic;

    @NotNull
    @Getter
    @Setter
    @Size(max = 256)
    @Column(name = "role_name")
    private String roleName;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    @Getter
    @Setter
    private Set<Permission> permissions = new HashSet<>();
}
