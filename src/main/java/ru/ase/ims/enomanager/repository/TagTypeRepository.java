package ru.ase.ims.enomanager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ase.ims.enomanager.model.TagType;

import java.util.List;
import java.util.Optional;

public interface TagTypeRepository extends CrudRepository<TagType, Long> {
    Optional<TagType> findByName(String name);

    //@Query("FROM TagType t ORDER BY t.name")
    List<TagType> findAllByOrderByNameAsc();

    void deleteByName(String name);
}
