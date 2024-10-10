package com.karpo.quisy.services;

import com.karpo.quisy.dtos.NewWorkbookDto;
import com.karpo.quisy.dtos.UpdateWorkbookDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.exceptions.NotFoundWorkbookException;
import com.karpo.quisy.helpers.TagBuilder;
import com.karpo.quisy.helpers.UserBuilder;
import com.karpo.quisy.helpers.WorkbookBuilder;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class WorkbookServiceTest {
    private UserBuilder userBuilder = new UserBuilder();
    private WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    private TagBuilder tagBuilder = new TagBuilder();

    @MockBean
    UserService userService;
    @MockBean
    TagService tagService;
    @MockBean
    WorkbookTagService workbookTagService;
    @MockBean
    WorkbookRepository workbookRepository;
    @MockBean
    TagRepository tagRepository;

    @Autowired
    WorkbookService workbookService;

    @Test
    @DisplayName("Search a Workbook By Id")
    void getWorkbook() {
        // Given
        User user = userBuilder.one(0);
        Workbook workbook = workbookBuilder.one(0, user);

        when(workbookRepository.findById(anyLong())).thenReturn(Optional.of(workbook));

        // When
        Workbook foundWorkbook = workbookService.getWorkbook((long) 0);

        // Then
        assertEquals(workbook, foundWorkbook);
    }

    @Test
    @DisplayName("Search a Workbook By Id Failed: Not Found Workbook")
    void getWorkbookNotFoundWorkbook() {
        // Given
        given(workbookRepository.findById(anyLong())).willReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundWorkbookException.class, () -> {
            workbookService.getWorkbook((long) 0);
        });
    }

    @Test
    @DisplayName("Search Workbooks without title and tags")
    void getWorkbooks() {
        // Given
        when(workbookRepository.getAllWorkbookPreviews()).thenReturn(workbookBuilder.workbookPreviewDto.many(3));
        when(tagRepository.findByWorkbookId(anyList())).thenReturn(tagBuilder.tagWithWorkbookIdDto.many(3, 2));

        // When
        List<WorkbookPreviewDto> workbooks = workbookService.getWorkbooks(null, null);

        // Then
        for(int i=0; i<workbooks.size(); i++) {
            WorkbookPreviewDto dto = workbooks.get(i);

            assertEquals("Title " + i, dto.getTitle());
            for(int j=0; j<dto.getTags().size(); j++) {
                String tag = dto.getTags().get(j);

                assertEquals("Tag " + j, tag);
            }
        }
    }

    @Test
    @DisplayName("Search Workbooks by title")
    void getWorkbooksByTitle() {
        // Given
        when(workbookRepository.getAllWorkbookPreviewsByTitle(anyString())).thenReturn(workbookBuilder.workbookPreviewDto.many(3));
        when(tagRepository.findByWorkbookId(anyList())).thenReturn(tagBuilder.tagWithWorkbookIdDto.many(3, 2));

        // When
        List<WorkbookPreviewDto> workbooks = workbookService.getWorkbooks("1", null);

        // Then
        assertEquals(3, workbooks.size());
    }

    @Test
    @DisplayName("Search Workbooks by tags")
    void getWorkbooksByTags() {
        // Given
        when(workbookRepository.getAllWorkbookPreviewsByTags(anyList())).thenReturn(workbookBuilder.workbookPreviewDto.many(3));
        when(tagRepository.findByWorkbookId(anyList())).thenReturn(tagBuilder.tagWithWorkbookIdDto.many(3, 2));

        // When
        List<WorkbookPreviewDto> workbooks = workbookService.getWorkbooks(null, new ArrayList<>());

        // Then
        assertEquals(3, workbooks.size());
    }

    @Test
    @DisplayName("Search Workbooks by title and tags")
    void getWorkbooksByTitleAndTags() {
        // Given
        when(workbookRepository.getAllWorkbookPreviewsByTitleAndTags(anyString(), anyList())).thenReturn(workbookBuilder.workbookPreviewDto.many(3));
        when(tagRepository.findByWorkbookId(anyList())).thenReturn(tagBuilder.tagWithWorkbookIdDto.many(3, 2));

        // When
        List<WorkbookPreviewDto> workbooks = workbookService.getWorkbooks("Title", new ArrayList<>());

        // Then
        assertEquals(3, workbooks.size());
    }

    @Test
    @DisplayName("Create new Workbook")
    void createWorkbook() {
        // Given
        User user = userBuilder.one(0);
        List<Tag> tags = tagBuilder.many(10);
        List<String> tagNames = new ArrayList<>();
        for(Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        Workbook workbook = workbookBuilder.one(0, user);

        NewWorkbookDto newWorkbookDto = new NewWorkbookDto();
        newWorkbookDto.setUserId((long) 0);
        newWorkbookDto.setTitle("String");
        newWorkbookDto.setDescription("String");
        newWorkbookDto.setTags(tagNames);

        when(userService.getUserById(anyLong())).thenReturn(user);
        when(tagService.createTags(anyList())).thenReturn(tags);
        when(workbookRepository.save(any())).thenReturn(workbook);

        // When
        WorkbookPreviewDto workbookPreviewDto = workbookService.createWorkbook(newWorkbookDto);

        // Then
        assertEquals(0, workbookPreviewDto.getWorkbookId());
        assertEquals(0, workbookPreviewDto.getUserId());
        assertEquals(10, workbookPreviewDto.getTags().size());
    }

    @Test
    @DisplayName("Update a Workbook")
    void updateWorkbook() {
        // Given
        UpdateWorkbookDto updateWorkbookDto = new UpdateWorkbookDto();
        updateWorkbookDto.setTitle("Updated Title");
        updateWorkbookDto.setDescription("Updated Description");
        List<String> newTagNames = new ArrayList<>();
        newTagNames.add("Tag name 0");
        newTagNames.add("Tag name 2");
        updateWorkbookDto.setTags(newTagNames);

        User user = userBuilder.one(0);
        Workbook workbook = workbookBuilder.one(0, user);
        List<Tag> tags = tagBuilder.many(2);
        List<WorkbookTag> oldWorkbookTags = workbookBuilder.addTags(workbook, tags);
        Tag newTag = tagBuilder.one(2);
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);

        given(workbookRepository.findById((long) 0)).willReturn(Optional.of(workbook));
        given(workbookTagService.getWorkbookTagsByWorkbook(workbook)).willReturn(oldWorkbookTags);
        given(tagService.createTags(any())).willReturn(newTags);

        // When
        WorkbookPreviewDto workbookPreviewDto = workbookService.updateWorkbook((long) 0, updateWorkbookDto);

        // Then
        verify(workbookTagService).deleteAll(any());
        verify(workbookTagService).addTagToWorkbook(workbook, newTags);
        assertEquals("Updated Title", workbookPreviewDto.getTitle());
        assertEquals(2, workbookPreviewDto.getTags().size());
    }
}