package com.karpo.quisy.controllers;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.services.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuestionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuestionService questionService;

    @Test
    @DisplayName("Search Questions By WorkbookId")
    void getQuestionsByWorkbookId() throws Exception {
        // Given
        List<QuestionPreviewDto> questionPreviewDtos = new ArrayList<>();
        questionPreviewDtos.add(QuestionPreviewDto.builder().questionId(0L).build());
        questionPreviewDtos.add(QuestionPreviewDto.builder().questionId(1L).build());
        questionPreviewDtos.add(QuestionPreviewDto.builder().questionId(2L).build());

        given(questionService.getQuestionsByWorkbookId(anyLong())).willReturn(questionPreviewDtos);

        // When & Then
        mvc.perform(get("/api/v1/workbook/0/questions")).andExpect(jsonPath("$.size()").value(3));
    }
}