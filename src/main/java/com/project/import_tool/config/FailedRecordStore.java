package com.project.import_tool.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FailedRecordStore {

    private List<String> failedRecords = new ArrayList<>();

    public void add(String line,String error){
        failedRecords.add(line+","+error);
    }

    public List<String>getAll(){
        return failedRecords;
    }

    public void clear(){
        failedRecords.clear();
    }




}
