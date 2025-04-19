package com.project.import_tool.repository;

import com.project.import_tool.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    Boolean existsByAccountName(String accountName);
}
