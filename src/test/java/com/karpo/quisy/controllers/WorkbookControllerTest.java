package com.karpo.quisy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karpo.quisy.dtos.NewWorkbookDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.helpers.WorkbookBuilder;
import com.karpo.quisy.services.WorkbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WorkbookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkbookBuilder workbookBuilder;

    @MockBean
    private WorkbookService workbookService;

    @Test
    void getWorkbooks() throws Exception {
        // Given
        List<WorkbookPreviewDto> workbookPreviewDtos = workbookBuilder.workbookPreviewDto.many(10);
        for(int i=0; i<workbookPreviewDtos.size(); i++) {
            WorkbookPreviewDto workbookPreviewDto = workbookPreviewDtos.get(i);

            List<String> tags = new ArrayList<>();
            for(int j=i; j<i+3; j++) tags.add("Tag " + j);
            workbookPreviewDto.setTags(tags);
        }

        given(workbookService.getWorkbooks(any(), any())).willReturn(workbookPreviewDtos);

        // When & Then
        mvc.perform(get("/api/v1/workbooks")).andExpect(jsonPath("$[3].title").value("Title 3"));
    }

    @Test
    void createWorkbook() throws Exception {
        // Given
        List<String> tags = new ArrayList<>();
        tags.add("Tag 1");
        tags.add("Tag 2");
        tags.add("Tag 3");

        NewWorkbookDto newWorkbookDto = new NewWorkbookDto();
        newWorkbookDto.setUserId((long) 0);
        newWorkbookDto.setTitle("Title");
        newWorkbookDto.setDescription("Description");
        newWorkbookDto.setTags(tags);

        WorkbookPreviewDto workbookPreviewDto = workbookBuilder.workbookPreviewDto.one(0);
        workbookPreviewDto.setTags(tags);

        given(workbookService.createWorkbook(any())).willReturn(workbookPreviewDto);

        // When & Then
        mvc.perform(
            post("/api/v1/workbook")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newWorkbookDto))
        ).andExpect(jsonPath("$.title").value("Title 0"));
    }
}