package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.import_tool.model.Account;
import com.project.import_tool.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImplementAccountService implements AccountService{

    public final AccountRepository accountRepository;

    @Autowired
    public ImplementAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public JsonNode getAccountMetaData() {
        ObjectMapper mapper = new ObjectMapper();

        //creating rootNode
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("entity","Accounts");

        ArrayNode propertiesNode = mapper.createArrayNode();

        // accountName metaData
        ObjectNode accountNameField = mapper.createObjectNode();

        accountNameField.put("fieldName","accountName");
        accountNameField.put("fieldType","String");
        accountNameField.put("isMandatory",true);

        ObjectNode accountNameConstraints = mapper.createObjectNode();
        accountNameConstraints.put("minLength",1);
        accountNameConstraints.put("maxLength",50);

        accountNameField.set("constraints",accountNameConstraints);

        // accountType metaData
        ObjectNode accountTypeField = mapper.createObjectNode();
        accountTypeField.put("fieldName","accountType");
        accountTypeField.put("fieldType","picklist");
        ArrayNode options = mapper.createArrayNode();
        options.add("Customer");
        options.add("Prospect");
        accountTypeField.set("options",options);
        accountTypeField.put("isMandatory",true);

        // industry metaData
        ObjectNode industryField = mapper.createObjectNode();
        industryField.put("fieldName","industry");
        industryField.put("fieldType","String");
        ObjectNode industryConstraints = mapper.createObjectNode();
        industryConstraints.put("minLength",1);
        industryConstraints.put("maxLength",50);
        industryField.set("constraints",industryConstraints);
        industryField.put("isMandatory",false);

        //adding to properties arrayNode
        propertiesNode.add(accountNameField);
        propertiesNode.add(accountTypeField);
        propertiesNode.add(industryField);

        //inserting into rootNode
        rootNode.put("properties",propertiesNode);
        return rootNode;
    }

    @Override
    public Account addAccountData(String accountName, String accountType, String industry) {
        if(accountName == null || accountName.isEmpty() || accountType == null || accountType.isEmpty() || industry.isEmpty() || industry == null){
            throw new IllegalStateException("Provide all fields");
        }

        if(accountRepository.existsByAccountName(accountName)){
            throw new IllegalStateException("Account Name already taken");
        }

        Account account = new Account(accountName,accountType,industry);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account updateAccountData(Long id, Map<String,Object> updates) {
        Optional<Account>existingAccount = accountRepository.findById(id);
        System.out.println("Updates: "+updates);
        if(existingAccount.isEmpty()){
            throw new IllegalStateException("Invalid id");
        }

        Account account = existingAccount.get();

        if(updates.containsKey("accountName")){

            String accountName = (updates.get("accountName")).toString();
            System.out.println(accountName);

            if(accountRepository.existsByAccountName(accountName)){
                throw new IllegalStateException("Account Name already taken");
            }

            account.setAccountName(accountName);
        }

        if(updates.containsKey("accountType")){
            String accountType = (updates.get("accountType")).toString();
            account.setAccountType(accountType);
        }

        if(updates.containsKey("industry")){
            String accountType = (updates.get("industry")).toString();
            account.setIndustry(accountType);
        }

        return accountRepository.save(account);
    }

    @Override
    public Account deleteAccountData(Long accountId) {
        Optional<Account>existingAccount = accountRepository.findById(accountId);
        if(existingAccount.isEmpty()){
            throw new IllegalStateException("Invalid id");
        }
        accountRepository.deleteById(accountId);
        return existingAccount.get();
    }

    @Override
    public Account getAccountsData(Long accountId) {
        if(accountId==null){
            throw new IllegalStateException("Provide account id");
        }
        Optional<Account>account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new IllegalStateException("Account with this id does not exists");
        }

        return account.get();
    }

    @Override
    public List<Account> getAllAccountData() {
        return accountRepository.findAll();
    }

    @Override
    public Account verifyMetadata() {
        return null;
    }
}
