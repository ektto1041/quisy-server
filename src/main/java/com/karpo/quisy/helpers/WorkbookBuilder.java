package com.karpo.quisy.helpers;

import com.karpo.quisy.entities.Tag;
import com.karpo.quisy.entities.User;
import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkbookBuilder {
    public Workbook one(int i, User user) {
        Workbook workbook = new Workbook();
        workbook.setTitle("WorkbookTitle " + i);
        workbook.setDescription("WorkbookDescription " + i);
        workbook.setUser(user);

        return workbook;
    }

    public List<Workbook> many(int max, User user) {
        List<Workbook> workbooks = new ArrayList<>();
        for(int i=0; i<max; i++) {
            workbooks.add(one(i, user));
        }

        return workbooks;
    }

    public WorkbookTag addTag(Workbook workbook, Tag tag) {
        WorkbookTag workbookTag = new WorkbookTag();
        workbookTag.setWorkbook(workbook);
        workbookTag.setTag(tag);

        return workbookTag;
    }

    public List<WorkbookTag> addTags(Workbook workbook, List<Tag> tags) {
        List<WorkbookTag> workbookTags = new ArrayList<>();
        for(Tag tag : tags) {
            workbookTags.add(addTag(workbook, tag));
        }

        return workbookTags;
    }
}
