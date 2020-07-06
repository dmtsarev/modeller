package ru.ase.ims.enomanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tags_type")
@NoArgsConstructor
@AllArgsConstructor
public class TagType {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "tags_type_sequence", sequenceName = "tags_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tags_type_sequence")
    @Column(name = "id")
    private Long id;

    @Size(max = 256)
    @Getter
    @Setter
    @NotNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Size(max = 256)
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Size(max = 256)
    private String color;


    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();
}
