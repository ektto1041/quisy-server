package com.karpo.quisy;

import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.repositories.TestRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import com.karpo.quisy.services.WorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {
    @Autowired
    private WorkbookRepository workbookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        workbookRepository.save(new Workbook("abc", "def"));
    }
}
