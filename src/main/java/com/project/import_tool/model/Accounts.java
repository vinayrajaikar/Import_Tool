package com.project.import_tool.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ACCOUNT_ID",nullable = false)
    private Long accountId;

    @Column(name = "ACCOUNT_NAME",nullable = false,unique = true)
    private String accountName;

    @Column(name = "ACCOUNT_TYPE",nullable = false)
    private String accountType;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Users>users = new ArrayList<>();

    public Accounts(String accountName, String accountType) {
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public Accounts() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Users> getUsers() {
        return users;
    }
}
