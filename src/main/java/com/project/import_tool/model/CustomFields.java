package com.project.import_tool.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity(name = "CustomFields")
public class CustomFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOM_FIELD_ID",nullable = false)
    private int customFieldId;

    @Column(name = "ENTITY",nullable = false)
    private String entity;

    @Type(JsonBinaryType.class)
    @Column(name = "PROPERTIES",columnDefinition = "jsonb")
    private JsonNode properties;

    public int getCustomFieldId() {
        return customFieldId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public JsonNode getProperties() {
        return properties;
    }

    public void setProperties(JsonNode properties) {
        this.properties = properties;
    }


}
