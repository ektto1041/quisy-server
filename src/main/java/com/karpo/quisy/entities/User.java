package com.karpo.quisy.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
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
