package com.karpo.quisy.dtos;

import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkbookPreviewDto {
    private Long workbookId;
    private Long userId;
    private String userNickname;
    private String userProfileImage;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags = new ArrayList<>();

    public WorkbookPreviewDto(Long workbookId, Long userId, String userNickname, String userProfileImage, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.workbookId = workbookId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImage = userProfileImage;
        this.title = title;
        this.description = description;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public static WorkbookPreviewDto from(User user, Workbook workbook) {
        return WorkbookPreviewDto.builder()
            .workbookId(workbook.getWorkbookId())
            .userId(user.getUserId())
            .userNickname(user.getNickname())
            .userProfileImage(user.getProfileImage())
            .title(workbook.getTitle())
            .description(workbook.getDescription())
            .createdAt(workbook.getCreatedAt())
            .updatedAt(workbook.getUpdatedAt())
            .build();
    }
}
