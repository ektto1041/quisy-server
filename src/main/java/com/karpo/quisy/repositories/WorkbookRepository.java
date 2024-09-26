package com.karpo.quisy.repositories;

import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkbookRepository extends JpaRepository<Workbook, Long> {
    @Query("SELECT new com.karpo.quisy.dtos.WorkbookPreviewDto(w.workbookId, u.userId, u.nickname, u.profileImage, w.title, w.description, w.createdAt, w.updatedAt) " +
            "FROM Workbook w " +
            "JOIN w.user u")
    List<WorkbookPreviewDto> getAllWorkbookPreviews();

    @Query("SELECT new com.karpo.quisy.dtos.WorkbookPreviewDto(w.workbookId, u.userId, u.nickname, u.profileImage, w.title, w.description, w.createdAt, w.updatedAt) " +
            "FROM Workbook w " +
            "JOIN w.user u " +
            "WHERE w.title LIKE %:title% ")
    List<WorkbookPreviewDto> getAllWorkbookPreviewsByTitle(@Param("title") String title);

    @Query("SELECT new com.karpo.quisy.dtos.WorkbookPreviewDto(w.workbookId, u.userId, u.nickname, u.profileImage, w.title, w.description, w.createdAt, w.updatedAt) " +
            "FROM WorkbookTag wt " +
            "JOIN wt.workbook w " +
            "JOIN wt.tag t " +
            "JOIN w.user u " +
            "WHERE t.name IN %:tags% ")
    List<WorkbookPreviewDto> getAllWorkbookPreviewsByTags(@Param("tags") List<String> tags);

    @Query("SELECT new com.karpo.quisy.dtos.WorkbookPreviewDto(w.workbookId, u.userId, u.nickname, u.profileImage, w.title, w.description, w.createdAt, w.updatedAt) " +
            "FROM WorkbookTag wt " +
            "JOIN wt.workbook w " +
            "JOIN wt.tag t " +
            "JOIN w.user u " +
            "WHERE w.title LIKE %:title% AND t.name IN %:tags%" )
    List<WorkbookPreviewDto> getAllWorkbookPreviewsByTitleAndTags(@Param("title") String title, @Param("tags") List<String> tags);

}


