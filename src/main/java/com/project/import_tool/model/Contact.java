package com.project.import_tool.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity(name = "Contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID",nullable = false)
    private Long contactId;

    @Column(name = "EMAIL",nullable = false,unique = true)
    private String email;

    @Column(name = "PHONE_NUMBER",nullable = false,unique = true)
    private Long phoneNumber;

    @Column(name = "ADDRESS",nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Type(JsonBinaryType.class)
    @Column(name = "CUSTOM_FIELDS" ,columnDefinition = "jsonb")
    private JsonNode customFields;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JsonNode getCustomFields() {
        return customFields;
    }

    public void setCustomFields(JsonNode customFields) {
        this.customFields = customFields;
    }

    public Long getContactId() {
        return contactId;
    }

}
