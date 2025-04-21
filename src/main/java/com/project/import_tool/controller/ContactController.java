package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.service.ImplementContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ImplementContactService contactService;

    public ContactController(ImplementContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/get-contact-metadata")
    public ResponseEntity<Map<String,Object>> getContactsMetaData(){
        JsonNode metaData = contactService.getContactMetadata();
        Map<String,Object>response = new HashMap<>();
        response.put("message","Contact metaData fetched Successfully");
        response.put("metaData",metaData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
