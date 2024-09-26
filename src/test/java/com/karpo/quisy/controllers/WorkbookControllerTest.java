package com.karpo.quisy.controllers;

import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.helpers.WorkbookBuilder;
import com.karpo.quisy.services.WorkbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
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
}