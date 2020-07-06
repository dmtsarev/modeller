package ru.ase.ims.enomanager.entity;

import lombok.Getter;
import ru.ase.ims.enomanager.model.Tag;
import ru.ase.ims.enomanager.model.TagType;


public class TagsBuilder {

    public static TagsBuilderEx getTagsBuilder() {
        return new TagsBuilderEx();
    }

    public static class TagsBuilderEx {

        @Getter
        private Tag tag;

        TagsBuilderEx() {
            tag = new Tag();
        }

        public TagsBuilderEx id(Long id) {
            this.tag.setId(id);
            return this;
        }

        public TagsBuilderEx name(String name) {
            this.tag.setName(name);
            return this;
        }

        public TagsBuilderEx description(String description) {
            this.tag.setDescription(description);
            return this;
        }

        public TagsBuilderEx type(String type) {
            TagType tagType = new TagType();
            tagType.setName(type);
            this.tag.setType(tagType);
            return this;
        }
    }
}
