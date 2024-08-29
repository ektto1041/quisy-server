package com.karpo.quisy;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.UserRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import com.karpo.quisy.repositories.WorkbookTagRepository;
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
    }
}
