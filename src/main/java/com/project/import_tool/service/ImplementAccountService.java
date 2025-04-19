package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.import_tool.model.Accounts;
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

        ObjectNode constraints = mapper.createObjectNode();
        constraints.put("minLength",1);
        constraints.put("maxLength",50);

        accountNameField.set("constraints",constraints);

        // accountType metaData
        ObjectNode accountTypeField = mapper.createObjectNode();
        accountTypeField.put("fieldName","accountType");
        accountTypeField.put("fieldType","picklist");
        ArrayNode options = mapper.createArrayNode();
        options.add("Customer");
        options.add("Prospect");
        accountTypeField.set("options",options);
        accountTypeField.put("isMandatory",true);

        //adding to properties arrayNode
        propertiesNode.add(accountNameField);
        propertiesNode.add(accountTypeField);

        //inserting into rootNode
        rootNode.put("properties",propertiesNode);
        return rootNode;
    }

    @Override
    public Accounts addAccountData(String accountName, String accountType) {
        if(accountName == null || accountName.isEmpty() || accountType == null || accountType.isEmpty()){
            throw new IllegalStateException("Provide all fields");
        }

        if(accountRepository.existsByAccountName(accountName)){
            throw new IllegalStateException("Account Name already taken");
        }

        Accounts account = new Accounts(accountName,accountType);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Accounts updateAccountData(Long id, Map<String,Object> updates) {
        Optional<Accounts>existingAccount = accountRepository.findById(id);
        System.out.println("Updates: "+updates);
        if(existingAccount.isEmpty()){
            throw new IllegalStateException("Invalid id");
        }

        Accounts account = existingAccount.get();

        if(updates.containsKey("accountName")){
            //Converting Object to String
            System.out.println("***");
            String accountName = (updates.get("accountName")).toString();

            System.out.println(accountName);
            System.out.println("***");

            if(accountRepository.existsByAccountName(accountName)){
                throw new IllegalStateException("Account Name already taken");
            }

            account.setAccountName(accountName);
        }

        if(updates.containsKey("accountType")){
            String accountType = (updates.get("accountType")).toString();
            account.setAccountType(accountType);
        }

        return accountRepository.save(account);
    }

    @Override
    public Accounts deleteAccountData(Long accountId) {
        Optional<Accounts>existingAccount = accountRepository.findById(accountId);
        if(existingAccount.isEmpty()){
            throw new IllegalStateException("Invalid id");
        }
        accountRepository.deleteById(accountId);
        return existingAccount.get();
    }

    @Override
    public Accounts getAccountsData(Long accountId) {
        if(accountId==null){
            throw new IllegalStateException("Provide account id");
        }
        Optional<Accounts>account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new IllegalStateException("Account with this id does not exists");
        }

        return account.get();
    }

    @Override
    public List<Accounts> getAllAccountData() {
        return accountRepository.findAll();
    }
}
