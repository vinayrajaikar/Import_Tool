package com.project.import_tool.config;

import com.project.import_tool.model.Accounts;
import org.springframework.batch.item.ItemProcessor;

public class EntityProcessor implements ItemProcessor<Accounts,Accounts> {
    @Override
    public Accounts process(Accounts account) throws Exception {
        return account;
    }
}
