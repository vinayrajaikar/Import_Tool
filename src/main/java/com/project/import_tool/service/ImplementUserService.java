package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplementUserService implements UserService {

    private final CustomFieldService customFieldService;

    @Autowired
    public ImplementUserService(CustomFieldService customFieldService) {
        this.customFieldService = customFieldService;
    }

    @Override
    public JsonNode getUserMetadata() {
        ObjectMapper mapper = new ObjectMapper();

        //creating rootNode
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("entity","Users");

        ArrayNode propertiesNode = mapper.createArrayNode();

        // firstName metaData
        ObjectNode firstNameField = mapper.createObjectNode();

        firstNameField.put("fieldName","firstName");
        firstNameField.put("fieldType","String");

        ObjectNode firstNameFieldConstraints = mapper.createObjectNode();
        firstNameFieldConstraints.put("isMandatory",true);
        firstNameFieldConstraints.put("unique",false);
        firstNameFieldConstraints.put("minLength",3);
        firstNameFieldConstraints.put("maxLength",15);

        firstNameField.set("constraints",firstNameFieldConstraints);

        // lastNameField metaData
        ObjectNode lastNameField = mapper.createObjectNode();

        lastNameField.put("fieldName","lastName");
        lastNameField.put("fieldType","String");

        ObjectNode lastNameFieldConstraints = mapper.createObjectNode();
        lastNameFieldConstraints.put("isMandatory",true);
        lastNameFieldConstraints.put("unique",false);
        lastNameFieldConstraints.put("minLength",3);
        lastNameFieldConstraints.put("maxLength",15);

        lastNameField.set("constraints",lastNameFieldConstraints);

        //adding to properties arrayNode
        propertiesNode.add(firstNameField);
        propertiesNode.add(lastNameField);

        //extraProperties
        JsonNode extraProperties = customFieldService.getUserExtraProperties();

        rootNode.put("lookup","accountId");
        rootNode.put("properties",propertiesNode);
        rootNode.put("extraProperties",extraProperties);

        return rootNode;
    }
}
