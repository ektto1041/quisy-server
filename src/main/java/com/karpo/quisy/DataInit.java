package com.karpo.quisy;

import com.karpo.quisy.entities.*;
import com.karpo.quisy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {
    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private WorkbookTagRepository workbookTagRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User newUser = User.builder().nickname("Park").email("asdf").accessToken("123").refreshToken("123").profileImage("image").isActive(true).provider("kakao").providerId("asdfafa").build();
        User savedUser = userRepository.save(newUser);

        Workbook workbook = new Workbook();
        workbook.setTitle("MyWorkbook");
        workbook.setDescription("asdfasdfasdf");
        workbook.setUser(savedUser);
        Workbook savedWorkbook = workbookRepository.save(workbook);

        Tag tag = new Tag();
        tag.setName("Programming");
        Tag savedTag = tagRepository.save(tag);

        WorkbookTag workbookTag = new WorkbookTag();
        workbookTag.setWorkbook(savedWorkbook);
        workbookTag.setTag(savedTag);
        workbookTagRepository.save(workbookTag);

        QuestionType questionType = new QuestionType();
        questionType.setName("JAVA");
        QuestionType savedQuestionType = questionTypeRepository.save(questionType);

        Question question = new Question();
        question.setQuestionType(savedQuestionType);
        question.setContent("What is this");
        question.setAnswer("asdf");
        question.setSequence(1);
        question.setWorkbook(savedWorkbook);
        questionRepository.save(question);
    }
}
