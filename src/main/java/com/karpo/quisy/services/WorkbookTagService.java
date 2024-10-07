package com.karpo.quisy.services;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.repositories.WorkbookTagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkbookTagService {
    private final WorkbookTagRepository workbookTagRepository;

    public WorkbookTagService(WorkbookTagRepository workbookTagRepository) {
        this.workbookTagRepository = workbookTagRepository;
    }

    public List<WorkbookTag> getWorkbookTagsByWorkbook(Workbook workbook) {
        return workbookTagRepository.findByWorkbook(workbook);
    }

    @Transactional
    public void addTagToWorkbook(Workbook workbook, List<Tag> tags) {
        List<WorkbookTag> workbookTags = new ArrayList<>();
        for(Tag tag : tags) {
            WorkbookTag workbookTag = new WorkbookTag();
            workbookTag.setWorkbook(workbook);
            workbookTag.setTag(tag);
            workbookTags.add(workbookTag);
        }

        workbookTagRepository.saveAll(workbookTags);
    }

    @Transactional
    public void deleteAll(List<WorkbookTag> workbookTagsToDelete) {
        workbookTagRepository.deleteAll(workbookTagsToDelete);
    }
}
