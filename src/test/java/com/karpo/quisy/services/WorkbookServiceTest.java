package com.karpo.quisy.services;

import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.helpers.TagBuilder;
import com.karpo.quisy.helpers.WorkbookBuilder;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkbookServiceTest {
    private WorkbookBuilder workbookBuilder;
    private TagBuilder tagBuilder;

    @Mock
    WorkbookRepository workbookRepository;
    @Mock
    TagRepository tagRepository;

    @InjectMocks
    WorkbookService workbookService;

    @BeforeEach
    void setUp() {
        this.workbookBuilder = new WorkbookBuilder();
        this.tagBuilder = new TagBuilder();
    }

    @Test
    void getWorkbooks() {
        System.out.println(workbookRepository);
        // Given
        when(workbookRepository.getAllWorkbookPreviews()).thenReturn(workbookBuilder.workbookPreviewDto.many(3));
        when(tagRepository.findByWorkbookId(anyList())).thenReturn(tagBuilder.tagWithWorkbookIdDto.many(3, 2));

        // When
        List<WorkbookPreviewDto> workbooks = workbookService.getWorkbooks();

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
}