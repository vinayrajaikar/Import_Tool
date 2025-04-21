package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.service.ImplementOpportunitiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunitiesController {
    private final ImplementOpportunitiesService opportunitiesService;

    public OpportunitiesController(ImplementOpportunitiesService opportunitiesService) {
        this.opportunitiesService = opportunitiesService;
    }

    @GetMapping("/get-opportunities-metadata")
    public ResponseEntity<Map<String,Object>> getContactsMetaData(){
        JsonNode metaData = opportunitiesService.getOpportunitiesMetadata();
        Map<String,Object>response = new HashMap<>();
        response.put("message","Opportunities metaData fetched Successfully");
        response.put("metaData",metaData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
