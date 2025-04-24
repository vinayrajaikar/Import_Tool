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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class SpringBatchConfig {

    @Autowired
    private AccountRepository accountRepository;

//    1. Custom Reader:
    @Bean
    public FlatFileItemReader<Accounts>reader(){
        return new FlatFileItemReaderBuilder<Accounts>()
                .name("accountItemReader")
                .resource(new FileSystemResource(System.getProperty("user.dir") + "/uploads/accounts.csv"))
                .linesToSkip(1)
                .lineMapper(customLineMapper())//maps each line of csv to object and return it.
                .targetType(Accounts.class)
                .build();
    }

    @Bean
    public LineMapper<Accounts> customLineMapper(){
        return new LineMapper<Accounts>() {
            @Override
            public Accounts mapLine(String line, int lineNumber) throws Exception {
                String[] fields = line.split(",");
//                System.out.println(Arrays.toString(fields));
                if(true){
                    Accounts account = new Accounts();
                    account.setAccountName(fields[0]);
                    account.setAccountType(fields[1]);
                    account.setIndustry(fields[2]);
                    return account;
                }
                return null;
            }
        };
    }

//    CustomReader customReader(){
//        return new CustomReader();
//    }

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
        writer.setMethodName("save");
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
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

}
