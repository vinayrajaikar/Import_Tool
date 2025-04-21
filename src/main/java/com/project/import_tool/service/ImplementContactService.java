package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class ImplementContactService implements ContactService{

    private final CustomFieldService customFieldService;

    public ImplementContactService(CustomFieldService customFieldService) {
        this.customFieldService = customFieldService;
    }

    @Override
    public JsonNode getContactMetadata() {
        ObjectMapper mapper = new ObjectMapper();

        //creating rootNode
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("entity","Contacts");

        ArrayNode propertiesNode = mapper.createArrayNode();

        // email metaData
        ObjectNode emailField = mapper.createObjectNode();

        emailField.put("fieldName","email");
        emailField.put("fieldType","String");

        ObjectNode emailFieldConstraints = mapper.createObjectNode();
        emailFieldConstraints.put("isMandatory",true);
        emailFieldConstraints.put("unique",false);
        emailFieldConstraints.put("length",10);

        emailField.set("constraints",emailFieldConstraints);

        // phoneNumber metaData
        ObjectNode phoneNumberField = mapper.createObjectNode();

        phoneNumberField.put("fieldName","phoneNumber");
        phoneNumberField.put("fieldType","Long");

        ObjectNode phoneNumberConstraints = mapper.createObjectNode();
        phoneNumberConstraints.put("isMandatory",true);
        phoneNumberConstraints.put("unique",false);
        phoneNumberConstraints.put("length",10);

        phoneNumberField.set("constraints",phoneNumberConstraints);

        // address metaData
        ObjectNode addressField = mapper.createObjectNode();

        addressField.put("fieldName","address");
        addressField.put("fieldType","String");

        ObjectNode addressConstraints = mapper.createObjectNode();
        addressConstraints.put("minLength",1);
        addressConstraints.put("maxLength",50);
        addressConstraints.put("isMandatory",false);
        addressConstraints.put("unique",false);

        addressField.set("constraints",addressConstraints);

        //adding to properties arrayNode
        propertiesNode.add(emailField);
        propertiesNode.add(phoneNumberField);
        propertiesNode.add(addressField);

        //extraProperties
        JsonNode extraProperties = customFieldService.getContactExtraProperties();

        rootNode.put("Lookup","userId");
        rootNode.put("properties",propertiesNode);
        rootNode.put("extraProperties",extraProperties);

        return rootNode;

    }
}
