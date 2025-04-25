package com.project.import_tool.config;

import com.project.import_tool.model.Account;
import org.springframework.batch.item.ItemProcessor;

public class EntityProcessor implements ItemProcessor<Account, Account> {
    @Override
    public Account process(Account account) throws Exception {
        return account;
    }
}
