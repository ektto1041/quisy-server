package com.karpo.quisy.repositories;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
@DataJpaTest
class WorkbookTagRepositoryTest {
    UserBuilder userBuilder = new UserBuilder();
    WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    TagBuilder tagBuilder = new TagBuilder();

    @Autowired UserRepository userRepository;
    @Autowired WorkbookRepository workbookRepository;
    @Autowired TagRepository tagRepository;
    @Autowired WorkbookTagRepository workbookTagRepository;

    @Test
    @DisplayName("Search WorkbookTag By Workbook")
    void findByWorkbook() {
        // Given
        User savedUser = userRepository.save(userBuilder.one(0));
        List<Workbook> savedWorkbooks = workbookRepository.saveAll(workbookBuilder.manyEntity(3, savedUser));
        List<Tag> savedTags = tagRepository.saveAll(tagBuilder.many(6));
        for(int i=0; i<3; i++) {
            List<Tag> tagsToAdd = new ArrayList<>();
            for(int j=0; j<2; j++) {
                tagsToAdd.add(savedTags.get((2*i) + j));
            }
            List<WorkbookTag> workbookTags = workbookBuilder.addTags(savedWorkbooks.get(i), tagsToAdd);
            for(WorkbookTag wt : workbookTags) {
                System.out.println("#" + wt.getWorkbook().getWorkbookId() + ", " + wt.getTag().getName());
            }
            workbookTagRepository.saveAll(workbookTags);
        }

        // When
        List<WorkbookTag> foundWorkbookTags = workbookTagRepository.findByWorkbook(savedWorkbooks.get(1));


        // Then
        assertEquals(2, foundWorkbookTags.size());
        assertEquals("Tag name 2", foundWorkbookTags.get(0).getTag().getName());
    }
}