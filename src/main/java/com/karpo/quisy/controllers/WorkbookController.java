package com.karpo.quisy.controllers;

import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.repositories.WorkbookRepository;
import com.karpo.quisy.services.WorkbookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WorkbookController {
    private final WorkbookService workbookService;

    public WorkbookController(WorkbookService workbookService) {
        this.workbookService = workbookService;
    }

    @RequestMapping(value = "workbooks", method = RequestMethod.GET)
    public List<WorkbookPreviewDto> getWorkbooks(@RequestParam(required = false) String title, @RequestParam(required = false) List<String> tags) {
        return workbookService.getWorkbooks();
    }
}
