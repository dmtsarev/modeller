package ru.ase.ims.enomanager.service;

import ru.ase.ims.enomanager.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<Tag> createTag(String name, String description, String type);
    Optional<Tag> getTag(String name);
    List<Tag> getTags();
    Optional<Tag> updateTag(Tag tag);
    void deleteTag(Long id);
    void deleteAllTags();
}
