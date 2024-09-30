package com.karpo.quisy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @OneToOne
    @JoinColumn(name = "question_type_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private QuestionType questionType;

    private String content;

    private String answer;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "workbook_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workbook workbook;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
