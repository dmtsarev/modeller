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
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "tags_sequence", sequenceName = "tags_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tags_sequence")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Size(max = 256)
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tags")
    @Getter
    @Setter
    @JsonIgnore
    private Set<EnoviaEntity> enoviaEntities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "type")
    @Getter
    @Setter
    private TagType type;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
