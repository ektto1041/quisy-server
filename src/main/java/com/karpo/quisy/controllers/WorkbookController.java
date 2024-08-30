package com.karpo.quisy.controllers;

import com.karpo.quisy.dtos.WorkbookPreviewDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WorkbookController {
    @RequestMapping(value = "workbooks", method = RequestMethod.GET)
    public WorkbookPreviewDto getWorkbooks(@RequestParam(required = false) String title, @RequestParam(required = false) List<String> tags) {
        return new WorkbookPreviewDto();
    }
}
