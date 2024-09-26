package com.karpo.quisy.repositories;

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
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
@DataJpaTest
class WorkbookRepositoryTest {
    UserBuilder userBuilder = new UserBuilder();
    WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    TagBuilder tagBuilder = new TagBuilder();

    @Autowired WorkbookRepository workbookRepository;
    @Autowired UserRepository userRepository;
    @Autowired TagRepository tagRepository;
    @Autowired WorkbookTagRepository workbookTagRepository;

    @Test
    @DisplayName("Search Workbooks")
    void getAllWorkbooks() {
        // Given
        List<User> savedUsers = userRepository.saveAll(userBuilder.many(10));
        List<Tag> savedTags = tagRepository.saveAll(tagBuilder.many(10));
        for(int i=0; i<savedUsers.size(); i++) {
            User savedUser = savedUsers.get(i);
            Workbook savedWorkbook = workbookRepository.save(workbookBuilder.one(i, savedUser));
            WorkbookTag savedWorkbookTag = workbookTagRepository.save(workbookBuilder.addTag(savedWorkbook, savedTags.get(i)));
        }

        // When
        List<WorkbookPreviewDto> allWorkbookPreviews = workbookRepository.getAllWorkbookPreviews();

        // Then
        assertEquals(10, allWorkbookPreviews.size());
    }

    @Test
    @DisplayName("Search Workbooks by title")
    void getWorkbooksByTitle() {
        // Given
        List<User> savedUsers = userRepository.saveAll(userBuilder.many(10));
        List<Tag> savedTags = tagRepository.saveAll(tagBuilder.many(10));
        for(int i=0; i<savedUsers.size(); i++) {
            User savedUser = savedUsers.get(i);
            Workbook savedWorkbook = workbookRepository.save(workbookBuilder.one(Math.min(i, 5), savedUser));
            WorkbookTag savedWorkbookTag = workbookTagRepository.save(workbookBuilder.addTag(savedWorkbook, savedTags.get(i)));
        }

        // When
        List<WorkbookPreviewDto> allWorkbookPreviews = workbookRepository.getAllWorkbookPreviewsByTitle("5");

        // Then
        assertEquals(5, allWorkbookPreviews.size());
    }
}