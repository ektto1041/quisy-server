package com.karpo.quisy;

import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.repositories.TestRepository;
import com.karpo.quisy.repositories.UserRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import com.karpo.quisy.services.WorkbookService;
import org.hibernate.jdbc.Work;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User newUser = User.builder().nickname("Park").email("asdf").accessToken("123").refreshToken("123").profileImage("image").isActive(true).provider("kakao").providerId("asdfafa").build();
        User savedUser = userRepository.save(newUser);

        Workbook workbook = new Workbook();
        workbook.setTitle("MyWorkbook");
        workbook.setDescription("asdfasdfasdf");
        workbook.setUser(savedUser);
        workbookRepository.save(workbook);
    }
}
