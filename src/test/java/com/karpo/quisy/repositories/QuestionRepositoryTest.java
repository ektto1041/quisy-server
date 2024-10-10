package com.karpo.quisy.repositories;

import com.karpo.quisy.dtos.QuestionPreviewDto;
import com.karpo.quisy.entities.Question;
import com.karpo.quisy.entities.QuestionType;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
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

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
class QuestionRepositoryTest {
    UserBuilder userBuilder = new UserBuilder();
    WorkbookBuilder workbookBuilder = new WorkbookBuilder();
    @Autowired UserRepository userRepository;
    @Autowired WorkbookRepository workbookRepository;
    @Autowired QuestionRepository questionRepository;
    @Autowired QuestionTypeRepository questionTypeRepository;

    @Test
    @DisplayName("Search Questions by WorkbookId")
    void findByWorkbook() {
        // Given
        User user = userBuilder.one(1);
        User savedUser = userRepository.save(user);

        Workbook workbook = workbookBuilder.oneEntity(1, savedUser);
        Workbook otherWorkbook = workbookBuilder.oneEntity(2, savedUser);
        Workbook savedWorkbook = workbookRepository.save(workbook);
        Workbook savedOtherWorkbook = workbookRepository.save(otherWorkbook);

        QuestionType oxType = QuestionType.builder().name("OX").build();
        QuestionType choiceType = QuestionType.builder().name("CHOICE").build();
        QuestionType savedOxType = questionTypeRepository.save(oxType);
        QuestionType savedChoiceType = questionTypeRepository.save(choiceType);

        Question oxQuestion = Question.builder().questionType(savedOxType).workbook(savedWorkbook).content("Content 1").answer("Answer 1").sequence(1L).build();
        Question choiceQuestion = Question.builder().questionType(savedChoiceType).workbook(savedWorkbook).content("Content 2").answer("Answer 2").sequence(2L).build();
        Question otherQuestion = Question.builder().questionType(savedChoiceType).workbook(savedOtherWorkbook).content("Content 3").answer("Answer 3").sequence(3L).build();
        questionRepository.save(oxQuestion);
        questionRepository.save(choiceQuestion);
        questionRepository.save(otherQuestion);

        // When
        List<QuestionPreviewDto> questionPreviewDtos = questionRepository.findByWorkbookId(1L);

        // Then
        assertEquals(2, questionPreviewDtos.size());
    }
}