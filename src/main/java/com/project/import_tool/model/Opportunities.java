package com.project.import_tool.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity(name = "Opportunities")
public class Opportunities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPPORTUNITY_ID",nullable = false)
    private Long opportunityId;

    @Column(name = "OPPORTUNITY_NAME",nullable = false)
    private String opportunityName;

    @Column(name = "CURRENCY",nullable = false)
    private String currency;

    @Column(name = "AMOUNT",nullable = false)
    private Long amount;

    @OneToOne
    @JoinColumn(name = "REFERENCE_USER_ID")
    private Users user;

    @Type(JsonBinaryType.class)
    @Column(name = "CUSTOM_FIELDS",columnDefinition = "jsonb")
    JsonNode customFields;

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public JsonNode getCustomFields() {
        return customFields;
    }

    public void setCustomFields(JsonNode customFields) {
        this.customFields = customFields;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

}
