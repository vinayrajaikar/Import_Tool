package com.project.import_tool.config;

import com.project.import_tool.model.Accounts;
import com.project.import_tool.repository.AccountRepository;
import org.springframework.batch.core.Entity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Autowired
    private AccountRepository accountRepository;

//    1. Custom Reader:
    @Bean
    CustomReader customReader(){
        return new CustomReader();
    }

//    2. Processor:
    @Bean
    EntityProcessor processor(){
        return new EntityProcessor();
    }

//    3. Writer:
    @Bean
    RepositoryItemWriter<Accounts>writer(){
        RepositoryItemWriter<Accounts>writer = new RepositoryItemWriter<>();
        writer.setRepository(accountRepository);
        writer.setMethodName("saveAll");
        return writer;
    }

//    --------------------------------------------------------------------------------------------------------
    //Defining job
    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return  new JobBuilder("importData",jobRepository)
            .start(step)
            .build();
    }

    //Defining step
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("csv-import-step",jobRepository)
                .<Accounts, Accounts>chunk(100, transactionManager)
                .reader(customReader())
                .processor(processor())
                .writer(writer())
//                .taskExecutor()
                .build();
    }

}
