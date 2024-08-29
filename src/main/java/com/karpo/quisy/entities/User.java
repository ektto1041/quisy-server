package com.karpo.quisy.entities;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 20)
    private String nickname;

    @Column
    private String email;

    @Column
    private String profileImage;

    @Column(length = 20)
    private String provider;

    @Column
    private String providerId;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

//    @Column
//    private LocalDateTime tokenExpiry;

    @Column
    private boolean isActive;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
