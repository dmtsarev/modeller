package ru.ase.ims.enomanager.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ase.ims.enomanager.entity.TagsBuilder;;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.model.TagType;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Disabled
public class TagsControllerTest extends AbstractSpringControllerTest {

    Tag tag = TagsBuilder.getTagsBuilder()
            .name("test_name_tag")
            .description("test_description")
            .getTag();

    @Before
    public void addTags() {
        Optional<TagType> tagType = repository.tagTypeRepository.findByName("Model");
        tag.setType(tagType.get());
        repository.tagsRepository.save(tag);
    }

    @After
    public void clearDB() {
        repository.tagsRepository.deleteAll();
    }

    @Test
    public void createUpdateTags() {

        Optional<TagType> tagType = repository.tagTypeRepository.findByName("Model");
        tag.setType(tagType.get());

        Tag createdTags = TagsBuilder.getTagsBuilder()
                .name("test_name_tag_2")
                .description("test_description_2")
                .getTag();

        createdTags.setType(tagType.get());

        var tags = controller.tagsController.createTags(createdTags);

        assertEquals(HttpStatus.CREATED, tags.getStatusCode());
        var tagFromDB = repository.tagsRepository.findByName("test_name_tag_2");
        assertTrue(tagFromDB.isPresent());
        assertFalse(tagFromDB.isEmpty());
        assertEquals("Model", tagFromDB.get().getType().getName());

        tags = controller.tagsController.createTags(TagsBuilder.getTagsBuilder()
                .name("test_name_tag_3")
                .description("test_description_3")
                .getTag());
        assertEquals(HttpStatus.CREATED, tags.getStatusCode());
        tagFromDB = repository.tagsRepository.findByName("test_name_tag_3");
        assertTrue(tagFromDB.isPresent());
        assertFalse(tagFromDB.isEmpty());
        assertNull(tagFromDB.get().getType());

        Tag updateTag = tags.getBody();
        updateTag.setName("update_name");

        tags = controller.tagsController.updateTags(updateTag);
        assertEquals(HttpStatus.OK, tags.getStatusCode());

        tagFromDB = repository.tagsRepository.findByName("update_name");
        assertTrue(tagFromDB.isPresent());
    }

    @Test
    public void getTags() {
        var tags = controller.tagsController.tags();
        assertEquals(HttpStatus.OK, tags.getStatusCode());
        assertFalse(controller.tagsController.tags().getBody().isEmpty());
    }

    @Test
    public void deleteAllTags() {
        controller.tagsController.deleteAllTag();
        assertTrue(((List<Tag>) repository.tagsRepository.findAll()).isEmpty());
    }

    @Test
    public void getTagsByType() {

        Tag tag1 = TagsBuilder.getTagsBuilder()
                .name("test_name_tag_3")
                .description("test_description")
                .getTag();

        repository.tagsRepository.save(tag1);

        var tags = controller.tagsController.tagsByType("Model");
        assertTrue(tags.getBody().stream().anyMatch(e -> e.getName().equals("test_name_tag")));

        var tags_nullType = controller.tagsController.tagsByType("None");
        assertTrue(tags_nullType.getBody().stream().anyMatch(e -> e.getName().equals("test_name_tag_3")));
    }

    @Test
    public void createndDeleteTagType() {
        TagType tagType = new TagType();
        tagType.setName("Created_Test");
        tagType.setColor("#4ca3dd");
        ResponseEntity<TagType> responsetagType = controller.tagsController.createTagsType(tagType);

        assertEquals(HttpStatus.CREATED, responsetagType.getStatusCode());
        assertTrue(repository.tagTypeRepository.findByName("Created_Test").isPresent());

        controller.tagsController.deleteTagTypeByType("Created_Test");
        assertFalse(repository.tagTypeRepository.findByName("Created_Test").isPresent());

        TagType tagTypeWithBadColor = new TagType();
        tagTypeWithBadColor.setName("Created_Test");
        tagTypeWithBadColor.setColor("bad_color");
        ResponseEntity<TagType> responsetagTypeWithBadColor = controller.tagsController.createTagsType(tagTypeWithBadColor);

        assertEquals(HttpStatus.BAD_REQUEST, responsetagTypeWithBadColor.getStatusCode());
    }
}
