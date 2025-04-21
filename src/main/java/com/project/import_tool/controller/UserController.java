package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.service.ImplementUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ImplementUserService userService;

    public UserController(ImplementUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-users-metadata")
    public ResponseEntity<Map<String,Object>> getUsersMetaData(){
        JsonNode metaData = userService.getUserMetadata();
        Map<String,Object>response = new HashMap<>();
        response.put("message","Users metaData fetched Successfully");
        response.put("metaData",metaData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
