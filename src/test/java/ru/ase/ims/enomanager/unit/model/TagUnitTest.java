package ru.ase.ims.enomanager.unit.model;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import ru.ase.ims.enomanager.entity.TagsBuilder;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.unit.repository.TagsRepositoryUnit;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Disabled
public class TagUnitTest {

    @Test
    public void crudTest() {

        Tag tag = TagsBuilder.getTagsBuilder()
                .id(1L)
                .name("test_tag_name")
                .description("test_description")
                .getTag();

        TagsRepositoryUnit tagsRepositoryUnit = new TagsRepositoryUnit();
        tagsRepositoryUnit.save(tag);

        assertFalse(((List<Tag>) tagsRepositoryUnit.findAll()).isEmpty());
        assertTrue(tagsRepositoryUnit.findById(1L).isPresent());
    }
}
