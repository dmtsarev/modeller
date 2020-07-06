package ru.ase.ims.enomanager.unit.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitCrudRepository<T, R> implements CrudRepository<T, R> {

    protected List<T> content = new ArrayList<>();

    @Override
    public <S extends T> S save(S entity) {
        content.add(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        content.addAll((List<S>) entities);
        return entities;
    }

    @Override
    public void delete(T entity) {
        content.remove(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        content.removeAll((List<T>) entities);
    }

    @Override
    public void deleteAll() {
        content.clear();
    }

    @Override
    public Iterable<T> findAll() {
        return content;
    }
}
