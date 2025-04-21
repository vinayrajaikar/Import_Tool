package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.model.Accounts;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface AccountService {
    public JsonNode getAccountMetaData();
    public Accounts addAccountData(String accountName, String accountType, String industry);
    public Accounts updateAccountData(Long id, Map<String,Object> updates);
    public Accounts deleteAccountData(Long accountId);
    public Accounts getAccountsData(Long accountId);
    public List<Accounts> getAllAccountData();
}
