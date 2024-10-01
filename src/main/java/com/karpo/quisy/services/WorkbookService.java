package com.karpo.quisy.services;

import com.karpo.quisy.dtos.NewWorkbookDto;
import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkbookService {
    private final UserService userService;
    private final TagService tagService;
    private final WorkbookTagService workbookTagService;
    private final WorkbookRepository workbookRepository;
    private final TagRepository tagRepository;

    public WorkbookService(UserService userService, TagService tagService, WorkbookTagService workbookTagService, WorkbookRepository workbookRepository, TagRepository tagRepository) {
        this.userService = userService;
        this.tagService = tagService;
        this.workbookTagService = workbookTagService;
        this.workbookRepository = workbookRepository;
        this.tagRepository = tagRepository;
    }

    public List<WorkbookPreviewDto> getWorkbooks(String title, List<String> tags) {
        List<WorkbookPreviewDto> result;

        if(title == null && tags == null) result = workbookRepository.getAllWorkbookPreviews();
        else if(tags == null) result = workbookRepository.getAllWorkbookPreviewsByTitle(title);
        else if(title == null) result = workbookRepository.getAllWorkbookPreviewsByTags(tags);
        else result = workbookRepository.getAllWorkbookPreviewsByTitleAndTags(title, tags);

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

    @Transactional
    public WorkbookPreviewDto createWorkbook(NewWorkbookDto newWorkbook) {
        User foundUser = userService.getUserById(newWorkbook.getUserId());
        List<Tag> tagsToSave = tagService.createTags(newWorkbook.getTags());

        Workbook workbook = new Workbook();
        workbook.setUser(foundUser);
        workbook.setTitle(newWorkbook.getTitle());
        workbook.setDescription(newWorkbook.getDescription());

        Workbook savedWorkbook = workbookRepository.save(workbook);

        workbookTagService.addTagToWorkbook(savedWorkbook, tagsToSave);

        WorkbookPreviewDto workbookPreviewDto = WorkbookPreviewDto.from(foundUser, savedWorkbook);
        workbookPreviewDto.setTags(newWorkbook.getTags());

        return workbookPreviewDto;
    }
}
