package com.karpo.quisy.repositories;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.karpo.quisy.dtos.QuestionPreviewDto(q.questionId, qt.name, q.content, q.answer, q.sequence, q.createdAt, q.updatedAt) " +
            "FROM Question q " +
            "JOIN q.questionType qt " +
            "JOIN q.workbook w "+
            "WHERE w.workbookId = :workbookId")
    List<QuestionPreviewDto> findByWorkbookId(@Param("workbookId") Long workbookId);
}
