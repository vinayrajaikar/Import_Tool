package com.project.import_tool.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener {

    @Autowired
    private FailedRecordStore failedRecordStore;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("job started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(failedRecordStore.getAll());
        System.out.println("Job completed hurayyy!!!!");
    }
}
