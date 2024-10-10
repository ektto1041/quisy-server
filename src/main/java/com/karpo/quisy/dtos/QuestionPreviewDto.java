package com.karpo.quisy.dtos;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionPreviewDto {
    private Long questionId;
    private String questionType;
    private String content;
    private String answer;

    public QuestionPreviewDto(Long questionId, String questionType, String content, String answer, Long sequence, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.content = content;
        this.answer = answer;
        this.sequence = sequence;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long sequence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // q.questionId, qt.name, q.content, q.answer, q.sequence, q.createdAt, q.updatedAt
}
