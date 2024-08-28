package com.karpo.quisy.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
//@Table(name = "workbook")
public class Workbook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workbookId;

    @Column(length = 50)
    private String title;

    @Column(length = 100)
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Workbook(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
