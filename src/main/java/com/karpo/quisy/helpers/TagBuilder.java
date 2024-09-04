package com.karpo.quisy.helpers;

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
}
