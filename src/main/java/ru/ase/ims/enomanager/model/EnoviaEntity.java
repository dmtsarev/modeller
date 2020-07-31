package ru.ase.ims.enomanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "entities")
@NoArgsConstructor
@AllArgsConstructor
public class EnoviaEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "entities_sequence", sequenceName = "entities_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "entities_sequence")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name")
    @Size(max = 256)
    @NotNull
    @Getter
    @Setter
    private String entityName;

    @ManyToOne
    @JoinColumn(name = "release_id", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Release release;

    @Column(name = "description", length = 10485760)
    @Getter
    @Setter
    private String description;

    @Column(name = "type")
    @NotNull
    @Getter
    @Setter
    private String type;

    @Transient
    @JsonInclude
    @Getter
    @Setter
    private Ematrix ematrix;

    @Transient
    @JsonInclude
    @Getter
    @Setter
    private String ematrixHTML;

    @Column(name = "file_name")
    @Size(max = 256)
    @NotNull
    @Getter
    @Setter
    private String fileName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tags_entity",
            joinColumns = {@JoinColumn(name = "entities_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "tags_id", referencedColumnName="id")}
    )
    @Setter
    @Getter
    private Set<Tag> tags = new HashSet<>();

    public EnoviaEntity(String fileName, Release release) {
        this.fileName = fileName;
        this.release = release;
        this.entityName = fileName;
    }

    public EnoviaEntity(String name, String type, String fileName, Release release) {
        this.entityName = name;
        this.type = type;
        this.release = release;
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnoviaEntity that = (EnoviaEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(entityName, that.entityName) &&
                Objects.equals(release, that.release) &&
                Objects.equals(description, that.description) &&
                Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityName, release, description, fileName);
    }

    @Override
    public String toString() {
        return "EnoviaEntity{" +
                "id=" + id +
                ", entityName='" + entityName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", ematrix=" + ematrix +
                ", ematrixHTML='" + ematrixHTML + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }


}
