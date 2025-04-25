package com.project.import_tool.config;

import com.project.import_tool.model.ImportData;
import com.project.import_tool.repository.ImportDataRepository;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobListener implements JobExecutionListener {

    @Autowired
    private FailedRecordStore failedRecordStore;

    @Autowired
    private ImportDataRepository importDataRepository;

    private ImportData importData;

    long totalRecordsCount = 0;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("job started");

        Path path = Paths.get(System.getProperty("user.dir") + "/uploads/accounts.csv");

        try {
            totalRecordsCount = Files.lines(path).count()-1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        importData = new ImportData();
        importData.setEntity("Accounts");
        importData.setInputCsvFile("");
        importData.setTotalRecordsCount(totalRecordsCount);
        importData.setImportStatus("In Progress");
        importDataRepository.save(importData);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String header = "AccountName,AccountType,Industry,Error";

        if(failedRecordStore.getAll().isEmpty()){ // remove chaining
            importData.setFailedRecords(header);
            importData.setImportStatus("Success");
            importDataRepository.save(importData);
        }

        String failedRecordsCsv = header + "\n" + String.join("\n", failedRecordStore.getAll());
        long failedRecordCount = failedRecordStore.getAll().size();

        if(failedRecordCount==totalRecordsCount){
            importData.setFailedRecords(failedRecordsCsv);
            importData.setImportStatus("Failed");
        }

        importData.setFailedRecords(failedRecordsCsv);
        importData.setImportStatus("Partial");
        importData.setFailedRecordsCount(failedRecordCount);
        importDataRepository.save(importData);

        failedRecordStore.clear();

        System.out.println("Job completed hurray!!!!");
    }

    public ImportData getImportData(){
        return importData;
    }
}
