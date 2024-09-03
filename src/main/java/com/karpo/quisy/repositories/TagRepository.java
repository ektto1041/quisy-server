package com.karpo.quisy.repositories;

import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(
        value = "SELECT new com.karpo.quisy.dtos.TagWithWorkbookIdDto(w.workbookId, t.name) " +
        "FROM WorkbookTag wt " +
        "JOIN wt.workbook w " +
        "JOIN wt.tag t " +
        "WHERE w.workbookId IN (:workbookIds)"
    )
    List<TagWithWorkbookIdDto> findByWorkbookId(List<Long> workbookIds);
}
