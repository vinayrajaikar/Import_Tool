package com.project.import_tool.repository;

import com.project.import_tool.model.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFieldsRepository extends JpaRepository<CustomField,Integer> {
    CustomField findByEntity(String entity);
}
