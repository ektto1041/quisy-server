package com.karpo.quisy.repositories;

import com.karpo.quisy.entities.WorkbookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbookTagRepository extends JpaRepository<WorkbookTag, Long> {
}
