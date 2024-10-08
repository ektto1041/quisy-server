package com.karpo.quisy.services;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionPreviewDto> getQuestionsByWorkbookId(Long workbookId) {
        return questionRepository.findByWorkbookId(workbookId);
    }
}
