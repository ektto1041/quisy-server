package com.karpo.quisy.services;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.helpers.TagBuilder;
import com.karpo.quisy.helpers.UserBuilder;
import com.karpo.quisy.helpers.WorkbookBuilder;
import com.karpo.quisy.repositories.WorkbookTagRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class WorkbookTagServiceTest {
    private UserBuilder userBuilder = new UserBuilder();
    private WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    private TagBuilder tagBuilder = new TagBuilder();

    @MockBean
    private WorkbookTagRepository workbookTagRepository;

    @Autowired
    private WorkbookTagService workbookTagService;

    @Test
    @DisplayName("Add Tags to Workbook")
    void addTagToWorkbook() {
        // Given
        User user = userBuilder.one(0);
        Workbook workbook = workbookBuilder.one(0, user);
        List<Tag> tags = tagBuilder.many(10);
        List<WorkbookTag> workbookTags = workbookBuilder.addTags(workbook, tags);

        // When
        workbookTagService.addTagToWorkbook(workbook, tags);

        // Then
        verify(workbookTagRepository, times(1)).saveAll(anyList());
    }
}