package com.project.import_tool.repository;

import com.project.import_tool.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Boolean existsByAccountName(String accountName);
}
