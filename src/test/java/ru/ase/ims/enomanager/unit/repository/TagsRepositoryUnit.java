package ru.ase.ims.enomanager.unit.repository;

import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.repository.TagsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagsRepositoryUnit extends UnitCrudRepository<Tag, Long> implements TagsRepository {
    @Override
    public Optional<Tag> findById(Long aLong) {
        return content.stream().filter(e -> e.getId().equals(aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Tag> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) { }

    @Override
    public Optional<Tag> findByName(String name) {
        return content.stream().filter(e -> e.getName().equals(name)).findFirst();
    }

    @Override
    public List<Tag> findByTypeIsNullOrderByNameAsc() {
        return content.stream().filter(e -> e.getType() == null).collect(Collectors.toList());
    }

    @Override
    public List<Tag> findAllByTypeNameOrderByNameAsc(String type) {
        return content.stream().filter(e -> e.getType().equals(type)).collect(Collectors.toList());
    }
}
