package com.karpo.quisy.repositories;

import com.karpo.quisy.QuisyApplication;
import com.karpo.quisy.configurations.MySQLConfiguration;
import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
class TagRepositoryTest {
    UserBuilder userBuilder = new UserBuilder();
    WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    TagBuilder tagBuilder = new TagBuilder();
    @Autowired WorkbookRepository workbookRepository;
    @Autowired UserRepository userRepository;
    @Autowired TagRepository tagRepository;
    @Autowired WorkbookTagRepository workbookTagRepository;

    @Test
    @DisplayName("WorkbookId로 모든 Tag 조회")
    void findByWorkbookId() {
        // Given
        User savedUser = userRepository.save(userBuilder.one(1));
        List<Tag> savedTags = tagRepository.saveAll(tagBuilder.many(9));
        List<Workbook> savedWorkbooks = workbookRepository.saveAll(workbookBuilder.many(3, savedUser));
        for(int i=0; i<savedWorkbooks.size(); i++) {
            Workbook savedWorkbook = savedWorkbooks.get(i);

            workbookTagRepository.saveAll(workbookBuilder.addTags(savedWorkbook, savedTags.subList(i*3, i*3+3)));
        }

        List<Long> workbookIds = savedWorkbooks.stream().map(Workbook::getWorkbookId).toList();

        // When
        List<TagWithWorkbookIdDto> result = tagRepository.findByWorkbookId(workbookIds);

        // Then
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                TagWithWorkbookIdDto dto = result.get(i*3+j);
                assertEquals(workbookIds.get(i), dto.getWorkbookId());
                assertEquals("Tag name " + (i*3+j), dto.getTag());
            }
        }
    }
}