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
class TagRepositoryTest {
    @Autowired
    UserBuilder userBuilder;
    @Autowired
    WorkbookBuilder workbookBuilder;
    @Autowired
    TagBuilder tagBuilder;
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