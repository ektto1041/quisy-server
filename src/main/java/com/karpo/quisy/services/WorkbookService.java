package com.karpo.quisy.services;

import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkbookService {
    private final WorkbookRepository workbookRepository;
    private final TagRepository tagRepository;

    public WorkbookService(WorkbookRepository workbookRepository, TagRepository tagRepository) {
        this.workbookRepository = workbookRepository;
        this.tagRepository = tagRepository;
    }

    public List<WorkbookPreviewDto> getWorkbooks() {
        List<WorkbookPreviewDto> result = workbookRepository.getAllWorkbookPreviews();
        List<Long> workbookIds = result.stream().map(WorkbookPreviewDto::getWorkbookId).toList();

        List<TagWithWorkbookIdDto> foundTags = tagRepository.findByWorkbookId(workbookIds);
        foundTags.forEach(tag -> {
            Long workbookId = tag.getWorkbookId();

            for (WorkbookPreviewDto workbookPreviewDto : result) {
                if (workbookPreviewDto.getWorkbookId().equals(workbookId)) {
                    workbookPreviewDto.getTags().add(tag.getTag());
                    break;
                }
            }
        });

        return result;
    }
}
