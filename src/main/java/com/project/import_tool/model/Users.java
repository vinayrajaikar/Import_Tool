package com.project.import_tool.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME",nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "REFERENCE_ACCOUNT_ID")
    public Accounts account;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Contacts contacts;

    @OneToOne(mappedBy = "user")
    private Opportunities opportunities;

    @Type(JsonBinaryType.class)
    @Column(name = "CUSTOM_FIELDS", columnDefinition = "jsonb")
    JsonNode customFields;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public Opportunities getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(Opportunities opportunities) {
        this.opportunities = opportunities;
    }

    public JsonNode getCustomFields() {
        return customFields;
    }

    public void setCustomFields(JsonNode customFields) {
        this.customFields = customFields;
    }
}
