package com.project.import_tool.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.import_tool.config.JobListener;
import com.project.import_tool.config.Launcher;
import com.project.import_tool.model.ImportData;
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

    @Autowired
    private JobListener jobListener;


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

        ImportData importData = jobListener.getImportData();

        if(importData == null){
             Map<String,Object> response = new HashMap<>();
             response.put("message", "Failed to start job");
             return ResponseEntity.status(500).body(response);
         }

        Map<String,Object> response = new HashMap<>();
        response.put("id",importData.getId());
        response.put("status",importData.getImportStatus());
        response.put("message","Job launched successfully");

        return ResponseEntity.ok(response);
    }
}
