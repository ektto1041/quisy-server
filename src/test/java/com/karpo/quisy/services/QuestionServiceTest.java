package com.karpo.quisy.services;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.repositories.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class QuestionServiceTest {
    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Search Questions by Workbook Id Service")
    void getQuestionsByWorkbookId() {
        // Given
        List<QuestionPreviewDto> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            list.add(QuestionPreviewDto.builder().questionId((long) i).questionType("OX").content("Content").answer("Answer").sequence((long) i).build());
        }

        given(questionRepository.findByWorkbookId(anyLong())).willReturn(list);

        // When
        List<QuestionPreviewDto> foundQuestions = questionService.getQuestionsByWorkbookId(0L);

        // Then
        assertEquals(3, foundQuestions.size());
    }
}