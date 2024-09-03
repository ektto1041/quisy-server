package com.karpo.quisy.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkbookPreviewDto {
    private Long workbookId;
    private Long userId;
    private String userNickname;
    private String userProfileImage;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;

    public WorkbookPreviewDto(Long workbookId, Long userId, String userNickname, String userProfileImage, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.workbookId = workbookId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImage = userProfileImage;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
