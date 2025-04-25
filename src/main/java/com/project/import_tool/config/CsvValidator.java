package com.project.import_tool.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CsvValidator {

    public Optional<String> validateWithMetadata(String[] fields, JsonNode schemaNode){
        ArrayNode properties = (ArrayNode) schemaNode.get("properties");
        int n = properties.size();
        for(int i=0;i<n;i++){

            //Extracting particular field schema
            JsonNode fieldSchema = properties.get(i);
            String fieldName = fieldSchema.get("fieldName").asText();
            String fieldType = fieldSchema.get("fieldType").asText();
            boolean isMandatory = fieldSchema.get("isMandatory").asBoolean();

            //defining value
            String value = i < fields.length ? fields[i].trim(): "";

            //1. Check Mandatory
            if(isMandatory && value.isEmpty()){
                return Optional.of(fieldName + "missing field");
            }

            //2. If not mandatory and value is empty , Skip it
            if(value.isEmpty()) continue;

            //3. Validation by type
            switch (fieldType){
                case "String":
                    if(fieldSchema.has("constraints")){
                        JsonNode constraints = fieldSchema.get("constraints");
                        int minLength = constraints.has("minLength") ? constraints.get("minLength").asInt():0;
                        int maxLength = constraints.has("maxLength") ? constraints.get("maxLength").asInt():50;

                        if(value.length() < minLength || value.length() > maxLength){
                            return Optional.of(fieldName+" must have length between "+ minLength+ " to "+maxLength);
                        }
                    }
                    break;

                case "picklist":
                    if(fieldSchema.has("options")){
                        ArrayNode options = (ArrayNode) fieldSchema.get("options");
                        System.out.println("option :" +options);
                        System.out.println("value :" +value);

                        boolean valid = false;
                        for (JsonNode option : options) {
                            if (option.asText().equals(value)) {
                                valid = true;
                                break;
                            }
                        }
                        if (!valid) {
                            return Optional.of("Invalid value for '" + fieldName + "': " + value);
                        }

                    }
                    break;
            }


        }

        return Optional.empty();// make it boolean

    }
}
