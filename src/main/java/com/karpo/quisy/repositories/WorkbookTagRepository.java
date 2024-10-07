package com.karpo.quisy.repositories;

import com.karpo.quisy.entities.Workbook;
import com.karpo.quisy.entities.WorkbookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkbookTagRepository extends JpaRepository<WorkbookTag, Long> {
    public List<WorkbookTag> findByWorkbook(Workbook workbook);
}
