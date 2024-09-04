package com.karpo.quisy.repositories;

import com.karpo.quisy.QuisyApplication;
import com.karpo.quisy.configurations.MySQLConfiguration;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.helpers.TagBuilder;
import com.karpo.quisy.helpers.UserBuilder;
import com.karpo.quisy.helpers.WorkbookBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com.karpo.quisy"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {QuisyApplication.class, MySQLConfiguration.class})
class WorkbookRepositoryTest {
    @Autowired UserBuilder userBuilder;
    @Autowired WorkbookBuilder workbookBuilder;
    @Autowired TagBuilder tagBuilder;
    @Autowired WorkbookRepository workbookRepository;
    @Autowired UserRepository userRepository;
    @Autowired TagRepository tagRepository;
    @Autowired WorkbookTagRepository workbookTagRepository;

    @Test
    @DisplayName("모든 Workbook 조회 - Tag 없는 결과")
    void getAllWorkbooks() {
        // Given
        User savedUser = userRepository.save(userBuilder.one(0));
        Tag savedTag = tagRepository.save(tagBuilder.one(0));
        Workbook savedWorkbook = workbookRepository.save(workbookBuilder.one(0, savedUser));
        WorkbookTag savedWorkbookTag = workbookTagRepository.save(workbookBuilder.addTag(savedWorkbook, savedTag));

        // When
        List<WorkbookPreviewDto> allWorkbookPreviews = workbookRepository.getAllWorkbookPreviews();

        // Then
        assertEquals(1, allWorkbookPreviews.size());
        assertEquals(0, allWorkbookPreviews.get(0).getTags().size());
        assertEquals("WorkbookTitle 0", allWorkbookPreviews.get(0).getTitle());
    }
}