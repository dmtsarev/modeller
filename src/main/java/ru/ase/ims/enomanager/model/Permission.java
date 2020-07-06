package ru.ase.ims.enomanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "mnemonic")
    @Getter
    @Setter
    @NotNull
    @Size(max=256)
    private String mnemonic;

    @Getter
    @Setter
    @NotNull
    @Column(name = "permission")
    private String permission;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;
}
