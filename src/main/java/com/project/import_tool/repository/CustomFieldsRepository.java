package com.project.import_tool.repository;

import com.project.import_tool.model.CustomFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFieldsRepository extends JpaRepository<CustomFields,Integer> {
    CustomFields findByEntity(String entity);
}
