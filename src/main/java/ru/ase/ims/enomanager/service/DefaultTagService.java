package ru.ase.ims.enomanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ase.ims.enomanager.exception.CreateReleaseException;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.model.TagType;
import ru.ase.ims.enomanager.repository.TagTypeRepository;
import ru.ase.ims.enomanager.repository.TagsRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTagService implements TagService {

    private final TagsRepository tagsRepository;
    private final TagTypeRepository tagTypeRepository;
    private static final String PATH_NONE_TAG_TYPE = "none";
    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";


    public List<Tag> getTags() {
        return (List<Tag>) tagsRepository.findAll();
    }

    public List<Tag> getTagsByType(String type) {
        if(type.toLowerCase().equals(PATH_NONE_TAG_TYPE)) {
            return tagsRepository.findByTypeIsNullOrderByNameAsc();
        }
        return tagsRepository.findAllByTypeNameOrderByNameAsc(type);
    }

    public List<TagType> getTagTypes() {
        return tagTypeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<Tag> createTag(String name, String description, String type) {
        return Optional.empty();
    }

    public Optional<Tag> getTag(String name) {
        return tagsRepository.findByName(name);
    }

    public Optional<Tag> updateTag(Tag tag) {

        if(tag.getId() == null || tag.getName().isBlank()) {
            log.info("Id or name tag is null");
            return Optional.empty();
        }

        Optional<Tag> tagFromDB = tagsRepository.findById(tag.getId());

        if(tagFromDB.isPresent()) {
            return Optional.of(tagsRepository.save(tag));
        } else {
            return Optional.empty();
        }
    }

    public Optional<TagType> updateTagType(TagType tagType) {

        if(tagType.getName() == null) {
            log.info("Name tag is null");
            return Optional.empty();
        }

        if(tagType.getName().isBlank()) {
            log.info("Name tag is empty");
            return Optional.empty();
        }

        Optional<TagType> tagTypeFromDB = tagTypeRepository.findById(tagType.getId());

        if(tagTypeFromDB.isPresent()) {
            return Optional.of(tagTypeRepository.save(tagType));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Tag> createTag(Tag tag) {

        if(tag == null) {
            log.info("Tag is null");
            return Optional.empty();
        }

        if(tag.getName().isBlank()) {
            log.info("Name tag is empty");
            return Optional.empty();
        }

        Optional.ofNullable(tag.getType())
                .ifPresent(e -> tagTypeRepository.findByName(e.getName())
                .ifPresentOrElse(tag::setType,
                                 () -> tag.setType(null)));

        log.debug("Create tag: " + "name: " + tag.getName());
        return Optional.of(tagsRepository.save(tag));
    }

    public Optional<TagType> createTagType(TagType tagType) {
        try {
            if (tagType.getName().isBlank()) {
                log.info("Name tags type is null");
                return Optional.empty();
            }

            if (!tagType.getColor().isBlank()) {
                Pattern pattern = Pattern.compile(HEX_PATTERN);
                if (!pattern.matcher(tagType.getColor()).matches()) {
                    log.info("Failed color matches: " + tagType.getColor());
                    return Optional.empty();
                }
            }

            log.debug("Create tags type: " + "name: " + tagType.getName());
            return Optional.of(tagTypeRepository.save(tagType));
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new CreateReleaseException(e.getMessage());
        }
    }

    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }

    public void deleteTagType(String name) {
        tagTypeRepository.deleteByName(name);
    }

    public void deleteAllTags() {
        tagsRepository.deleteAll();
    }
}
