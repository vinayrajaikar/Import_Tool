package com.project.import_tool.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class ImplementCustomFieldService implements CustomFieldService{

    @Override
    public JsonNode getContactExtraProperties() {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode propertiesArrayNode = mapper.createArrayNode();

        //website Node
        ObjectNode websiteField = mapper.createObjectNode();

        websiteField.put("fieldName","website");
        websiteField.put("fieldType","String");

        ObjectNode websiteFieldConstraints = mapper.createObjectNode();
        websiteFieldConstraints.put("unique",true);

        websiteField.set("constraints",websiteFieldConstraints);

        //postalCode Node
        ObjectNode postalCodeField = mapper.createObjectNode();

        postalCodeField.put("fieldName","postalCodeField");
        postalCodeField.put("fieldType","Long");

        ObjectNode postalCodeFieldConstraints = mapper.createObjectNode();
        postalCodeFieldConstraints.put("unique",false);
        postalCodeFieldConstraints.put("minLength",3);
        postalCodeFieldConstraints.put("maxLength",12);

        postalCodeField.set("constraints",postalCodeFieldConstraints);

        //adding fields to propertiesArrayNode
        propertiesArrayNode.add(websiteField);
        propertiesArrayNode.add(postalCodeField);

        //adding properties to root node
        rootNode.set("extraProperties",propertiesArrayNode);

        return rootNode.get("extraProperties");
    }

    @Override
    public JsonNode getOpportunitiesExtraProperties() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode propertiesArrayNode = mapper.createArrayNode();

        //priorityField Node
        ObjectNode priorityField = mapper.createObjectNode();

        priorityField.put("fieldName","priority");
        priorityField.put("fieldType","picklist");

        ArrayNode options =  mapper.createArrayNode();
        options.add("High");
        options.add("Medium");
        options.add("Low");

        priorityField.put("options",options);


        // closingDateField   Node
        ObjectNode closingDateField = mapper.createObjectNode();
        closingDateField.put("fieldName","closingDateField");
        closingDateField.put("fieldType","LocalDate");
        closingDateField.put("pattern","yyyy-MM-dd");

        ObjectNode closingDateFieldConstraints = mapper.createObjectNode();
        closingDateFieldConstraints.put("unique",false);
        closingDateFieldConstraints.put("pattern","yyyy-MM-dd");

        closingDateField.set("constraints",closingDateFieldConstraints);

        //adding fields to propertiesArrayNode
        propertiesArrayNode.add(priorityField);
        propertiesArrayNode.add(closingDateField);

        //adding properties to root node
        rootNode.set("extraProperties",propertiesArrayNode);

        return rootNode.get("extraProperties");
    }

    @Override
    public JsonNode getUserExtraProperties() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode propertiesArrayNode = mapper.createArrayNode();

        //genderField Node
        ObjectNode genderField = mapper.createObjectNode();

        genderField.put("fieldName","gender");
        genderField.put("fieldType","picklist");

        ArrayNode options =  mapper.createArrayNode();
        options.add("Male");
        options.add("Female");
        options.add("other");

        genderField.put("options",options);


        // DOBField Node
        ObjectNode DOBField = mapper.createObjectNode();
        DOBField.put("fieldName","DOB");
        DOBField.put("fieldType","LocalDate");
        DOBField.put("pattern","yyyy-MM-dd");

        ObjectNode DOBFieldConstraints = mapper.createObjectNode();
        DOBFieldConstraints.put("unique",false);
        DOBFieldConstraints.put("pattern","yyyy-MM-dd");

        DOBField.set("constraints",DOBFieldConstraints);

        //adding fields to propertiesArrayNode
        propertiesArrayNode.add(genderField);
        propertiesArrayNode.add(DOBField);

        //adding properties to root node
        rootNode.set("extraProperties",propertiesArrayNode);

        return rootNode.get("extraProperties");
    }

}
