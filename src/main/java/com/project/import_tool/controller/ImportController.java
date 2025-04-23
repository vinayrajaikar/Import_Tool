package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.config.Launcher;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImportController {

    @Autowired
    private Launcher launcher;


    @PostMapping(value = "/imports", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> importEntity(
            @RequestParam("entity") String entity,
            @RequestPart("columnMapping") JsonNode columnMapping,
            @RequestPart("file") MultipartFile file) throws IOException {

        //if it's not csv file
        if(!file.getContentType().equals("text/csv")){
            Map<String,Object>Response=new HashMap<>();
            Response.put("message","Only CSV files allowed!");
            return ResponseEntity.badRequest().body(Response);
        }

        //if file is empty
        if(file.isEmpty()){
            Map<String,Object>Response=new HashMap<>();
            Response.put("message","Please upload a valid file");
            return ResponseEntity.badRequest().body(Response);
        }

        System.out.println(file.getOriginalFilename());

        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
        String filePath = UPLOAD_DIR + "accounts.csv";
        file.transferTo(new File(filePath));

        launcher.jobLauncher();

        return null;
    }
}
