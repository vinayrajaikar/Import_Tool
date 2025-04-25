package com.project.import_tool.repository;

import com.project.import_tool.model.ImportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportDataRepository extends JpaRepository<ImportData,Long> {
}
