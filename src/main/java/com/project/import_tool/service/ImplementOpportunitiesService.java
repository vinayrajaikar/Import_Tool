package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplementOpportunitiesService implements OpportunitiesService{

    private final CustomFieldService customFieldService;

    @Autowired
    public ImplementOpportunitiesService(CustomFieldService customFieldService) {
        this.customFieldService = customFieldService;
    }

    @Override
    public JsonNode getOpportunitiesMetadata() {
        ObjectMapper mapper = new ObjectMapper();

        //creating rootNode
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("entity","Opportunities");

        ArrayNode propertiesNode = mapper.createArrayNode();

        // opportunityName metaData
        ObjectNode opportunityNameField = mapper.createObjectNode();

        opportunityNameField.put("fieldName","opportunityName");
        opportunityNameField.put("fieldType","String");

        ObjectNode opportunityNameFieldConstraints = mapper.createObjectNode();
        opportunityNameFieldConstraints.put("isMandatory",true);
        opportunityNameFieldConstraints.put("unique",false);
        opportunityNameFieldConstraints.put("minLength",3);
        opportunityNameFieldConstraints.put("maxLength",15);

        opportunityNameField.set("constraints",opportunityNameFieldConstraints);

        // currency metaData
        ObjectNode currencyField = mapper.createObjectNode();

        currencyField.put("fieldName","currencyField");
        currencyField.put("fieldType","String");

        ObjectNode currencyFieldConstraints = mapper.createObjectNode();
        currencyFieldConstraints.put("isMandatory",true);
        currencyFieldConstraints.put("unique",false);

        currencyField.set("constraints",currencyFieldConstraints);

        // amount metaData
        ObjectNode amountField = mapper.createObjectNode();

        amountField.put("fieldName","amount");
        amountField.put("fieldType","Long");

        ObjectNode amountFieldConstraints = mapper.createObjectNode();
        amountFieldConstraints.put("isMandatory",false);
        amountFieldConstraints.put("unique",false);

        amountField.set("constraints",amountFieldConstraints);

        //adding to properties arrayNode
        propertiesNode.add(opportunityNameField);
        propertiesNode.add(currencyField);
        propertiesNode.add(amountField);

        //extraProperties
        JsonNode extraProperties = customFieldService.getOpportunitiesExtraProperties();

        rootNode.put("Lookup","userId");
        rootNode.put("properties",propertiesNode);
        rootNode.put("extraProperties",extraProperties);

        return rootNode;
    }
}
