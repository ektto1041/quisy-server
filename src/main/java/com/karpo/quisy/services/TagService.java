package com.karpo.quisy.services;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public List<Tag> createTags(List<String> tags) {
        List<Tag> foundTags = tagRepository.findByName(tags);

        List<String> newTagNames = new ArrayList<>();
        for(String target : tags) {
            boolean isNew = true;
            for(Tag foundTag : foundTags) {
                if(target.equals(foundTag.getName())) {
                    isNew = false;
                    break;
                }
            }

            if(isNew) {
                newTagNames.add(target);
            }
        }

        List<Tag> newTags = new ArrayList<>();
        for(String newTagName : newTagNames) {
            Tag newTag = new Tag();
            newTag.setName(newTagName);

            newTags.add(newTag);
        }

        List<Tag> savedTags = tagRepository.saveAll(newTags);

        List<Tag> tagsToSave = new ArrayList<>();
        tagsToSave.addAll(foundTags);
        tagsToSave.addAll(savedTags);

        return tagsToSave;
    }
}
