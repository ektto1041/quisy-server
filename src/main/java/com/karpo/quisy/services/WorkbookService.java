package com.karpo.quisy.services;

import com.karpo.quisy.dtos.NewWorkbookDto;
import com.karpo.quisy.dtos.TagWithWorkbookIdDto;
import com.karpo.quisy.dtos.UpdateWorkbookDto;
import com.karpo.quisy.dtos.WorkbookPreviewDto;
import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import com.karpo.quisy.exceptions.NotFoundWorkbookException;
import com.karpo.quisy.repositories.TagRepository;
import com.karpo.quisy.repositories.WorkbookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Workbook getWorkbook(Long workbookId) {
        return workbookRepository.findById(workbookId).orElseThrow(() -> new NotFoundWorkbookException("UserId: " + workbookId + " 유저를 찾을 수 없습니다"));
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

    public WorkbookPreviewDto updateWorkbook(Long workbookId, UpdateWorkbookDto updateWorkbookDto) {
        Workbook foundWorkbook = getWorkbook(workbookId);
        foundWorkbook.setTitle(updateWorkbookDto.getTitle());
        foundWorkbook.setDescription(updateWorkbookDto.getDescription());

        List<String> newTagNames = updateWorkbookDto.getTags();
        // ["A", "B", "C"]

        List<WorkbookTag> foundWorkbookTags = workbookTagService.getWorkbookTagsByWorkbook(foundWorkbook);
        // ["A", "D",]

        HashMap<String, Integer> tagInfo = new HashMap<>();
        List<WorkbookTag> workbookTagsToDelete = new ArrayList<>();

        for(String newTagName : newTagNames) tagInfo.put(newTagName, 0b01);
        for(WorkbookTag foundWorkbookTag : foundWorkbookTags) {
            String foundTagName = foundWorkbookTag.getTag().getName();

            if(tagInfo.containsKey(foundTagName)) tagInfo.put(foundTagName, 0b11);
            else {
//                tagInfo.put(foundTagName, 0b10);
                workbookTagsToDelete.add(foundWorkbookTag);
            }
        }

        // delete tags
        workbookTagService.deleteAll(workbookTagsToDelete);

        // create Tags
        List<String> tagNamesToCreate = new ArrayList<>();
        for(String tagName : tagInfo.keySet()) {
            int flag = tagInfo.get(tagName);
            if(flag == 0b01) tagNamesToCreate.add(tagName);
        }
        List<Tag> newTags = tagService.createTags(tagNamesToCreate);

        workbookTagService.addTagToWorkbook(foundWorkbook, newTags);

        WorkbookPreviewDto workbookPreviewDto = WorkbookPreviewDto.from(foundWorkbook.getUser(), foundWorkbook);
        workbookPreviewDto.setTags(newTagNames);

        return workbookPreviewDto;
    }
}
