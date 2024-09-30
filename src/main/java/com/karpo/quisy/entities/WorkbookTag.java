package com.karpo.quisy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class WorkbookTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workbookTagId;

    @ManyToOne
    @JoinColumn(name = "workbook_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workbook workbook;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tag tag;
}
