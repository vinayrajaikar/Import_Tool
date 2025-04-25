package com.project.import_tool.repository;

import com.project.import_tool.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunity,Long> {
}
