package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.model.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
    public JsonNode getAccountMetaData();
    public Account addAccountData(String accountName, String accountType, String industry);
    public Account updateAccountData(Long id, Map<String,Object> updates);
    public Account deleteAccountData(Long accountId);
    public Account getAccountsData(Long accountId);
    public List<Account> getAllAccountData();
    public Account verifyMetadata();
}
