package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.model.Accounts;
import com.project.import_tool.service.ImplementAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final ImplementAccountService accountService;

    public AccountController(ImplementAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add-account")
    public ResponseEntity<Map<String,Object>>addAccount(@RequestBody Accounts account){
        Accounts addedAccount = accountService.addAccountData(account.getAccountName(),account.getAccountType());
        Map<String,Object>response=new HashMap<>();
        response.put("message","Account added Successfully");
        response.put("account",addedAccount);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-account/{accountId}")
    public ResponseEntity<Map<String,Object>>getAccount(@PathVariable Long accountId){
        Accounts account = accountService.getAccountsData(accountId);
        Map<String,Object>response = new HashMap<>();
        response.put("message","Account fetched successfully");
        response.put("account",account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-accounts")
    public ResponseEntity<Map<String,Object>>getAllAccounts(){
        List<Accounts>accounts=accountService.getAllAccountData();
        Map<String,Object>response = new HashMap<>();
        response.put("accounts",accounts);
        response.put("message","Accounts fetched successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update-account/{accountId}")
    public ResponseEntity<Map<String,Object>>updateAccount(@PathVariable Long accountId,
                                                           @RequestBody Map<String,Object>updates){
        System.out.println(updates);
        Accounts account = accountService.updateAccountData(accountId,updates);
        Map<String,Object>response = new HashMap<>();
        response.put("accounts",account);
        response.put("message","Account updated Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity<Map<String,Object>>deleteAccount(@PathVariable Long accountId){
        Accounts account = accountService.deleteAccountData(accountId);
        Map<String,Object>response = new HashMap<>();
        response.put("accounts",account);
        response.put("message","Account deleted Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get-account-metadata")
    public ResponseEntity<Map<String,Object>>getAccountsMetaData(){
        JsonNode metaData = accountService.getAccountMetaData();
        Map<String,Object>response = new HashMap<>();
        response.put("message","Account metaData fetched Successfully");
        response.put("metaData",metaData);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
