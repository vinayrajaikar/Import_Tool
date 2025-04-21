package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface CustomFieldService {
    public JsonNode getContactExtraProperties();
    public JsonNode getOpportunitiesExtraProperties();
    public JsonNode getUserExtraProperties();
}
