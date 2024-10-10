package com.karpo.quisy.services;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.helpers.TagBuilder;
import com.karpo.quisy.repositories.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class TagServiceTest {
    private TagBuilder tagBuilder = new TagBuilder();

    @MockBean
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Test
    @DisplayName("Create new tags")
    void createTags() {
        // Given
        List<Tag> foundTags = tagBuilder.many(10);
        List<Tag> savedTags = new ArrayList<>();
        for(int i=11; i<14; i++) savedTags.add(tagBuilder.one(i));

        when(tagRepository.findByName(anyList())).thenReturn(foundTags);
        when(tagRepository.saveAll(anyList())).thenReturn(savedTags);

        // When
        List<String> newTagNames = new ArrayList<>();
        for(int i=0; i<14; i++) newTagNames.add("Tag name " + i);
        List<Tag> newTags = tagService.createTags(newTagNames);

        // Then
        assertEquals(13, newTags.size());
    }
}