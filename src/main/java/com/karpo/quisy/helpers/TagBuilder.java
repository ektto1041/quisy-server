package com.karpo.quisy.helpers;

import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagBuilder {
    public Tag one(int i) {
        Tag tag = new Tag();
        tag.setName("Tag name " + i);

        return tag;
    }

    public List<Tag> many(int max) {
        List<Tag> tags = new ArrayList<>();
        for(int i=0; i<max; i++) {
            tags.add(one(i));
        }

        return tags;
    }

    public class TagWithWorkbookIdDtoBuilder {
        public TagWithWorkbookIdDto one(int workbookId, int tagId) {
            TagWithWorkbookIdDto tagWithWorkbookIdDto = new TagWithWorkbookIdDto();
            tagWithWorkbookIdDto.setWorkbookId((long) workbookId);
            tagWithWorkbookIdDto.setTag("Tag " + tagId);

            return tagWithWorkbookIdDto;
        }

        public List<TagWithWorkbookIdDto> many(int workbookCount, int max) {
            List<TagWithWorkbookIdDto> result = new ArrayList<>();
            for(int i=0; i<workbookCount; i++) {
                for(int j=0; j<max; j++) {
                    result.add(one(i, j));
                }
            }

            return result;
        }
    }
    public final TagWithWorkbookIdDtoBuilder tagWithWorkbookIdDto = new TagWithWorkbookIdDtoBuilder();
}
