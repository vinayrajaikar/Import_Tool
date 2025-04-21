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

    public enum AccountType{
        Customer,Prospect;

        public static AccountType fromString(String value) {
            try {
                return AccountType.valueOf(value.trim());
            } catch (Exception e) {
                throw new IllegalStateException("Invalid AccountType: " + value);
            }
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE",nullable = false)
    private AccountType accountType;

    @Column(name = "INDUSTRY")
    private String industry;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Users>users = new ArrayList<>();

    public Accounts(String accountName, String accountType, String industry) {
        this.accountName = accountName;
        this.accountType = AccountType.valueOf(accountType);
        this.industry = industry;
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
        return accountType.toString();
    }

    public void setAccountType(String accountType) {
        this.accountType = AccountType.fromString(accountType);
    }

    public List<Users> getUsers() {
        return users;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
