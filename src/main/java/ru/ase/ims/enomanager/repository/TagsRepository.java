package ru.ase.ims.enomanager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ase.ims.enomanager.model.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagsRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    List<Tag> findAllByTypeNameOrderByNameAsc(String type);

    List<Tag> findByTypeIsNullOrderByNameAsc();
}
