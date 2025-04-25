package com.project.import_tool.config;

import com.project.import_tool.model.Account;
import com.project.import_tool.repository.AccountRepository;
import com.project.import_tool.service.AccountService;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.Optional;

@Configuration
public class SpringBatchConfig {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CsvValidator csvValidator;
    private final JobListener jobListener;
    private final FailedRecordStore failedRecordStore;

    @Autowired
    public SpringBatchConfig(AccountRepository accountRepository, AccountService accountService, CsvValidator csvValidator, JobListener jobListener, FailedRecordStore failedRecordStore) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.csvValidator = csvValidator;
        this.jobListener = jobListener;
        this.failedRecordStore = failedRecordStore;
    }

//    1. Reader:
    @Bean
    public FlatFileItemReader<Account>reader(){
        return new FlatFileItemReaderBuilder<Account>()
                .name("accountItemReader")
                .resource(new FileSystemResource(System.getProperty("user.dir") + "/uploads/accounts.csv"))
                .linesToSkip(1)
                .lineMapper(customLineMapper())//maps each line of csv to object and return it.
                .targetType(Account.class)
                .build();
    }

    @Bean
    public LineMapper<Account> customLineMapper(){
        return new LineMapper<Account>() {
            @Override
            public Account mapLine(String line, int lineNumber) throws Exception {
                String[] fields = line.split(",");
                System.out.println(Arrays.asList(fields));
                Optional<String> error = csvValidator.validateWithMetadata(fields,accountService.getAccountMetaData());

                if(error.isPresent()){
                    System.out.println(error.get());
                    failedRecordStore.add(line,error.get());
                    return null;
                }

                Account account = new Account();
                account.setAccountName(fields[0]);
                account.setAccountType(fields[1]);
                account.setIndustry(fields[2]);
                return account;

            }
        };
    }

//    2. Processor:
    @Bean
    EntityProcessor processor(){
        return new EntityProcessor();
    }

//    3. Writer:
    @Bean
    RepositoryItemWriter<Account>writer(){
        RepositoryItemWriter<Account>writer = new RepositoryItemWriter<>();
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
            .listener(jobListener)
            .build();
    }

    //Defining step
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("csv-import-step",jobRepository)
                .<Account, Account>chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

}
