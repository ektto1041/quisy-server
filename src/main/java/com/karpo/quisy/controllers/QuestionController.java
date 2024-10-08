package com.karpo.quisy.controllers;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.services.QuestionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "workbook/{workbookId}/questions", method = RequestMethod.GET)
    public List<QuestionPreviewDto> getQuestionsByWorkbookId(@PathVariable Long workbookId) {
        return questionService.getQuestionsByWorkbookId(workbookId);
    }
}
