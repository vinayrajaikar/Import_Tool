package com.project.import_tool.config;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.project.import_tool.model.Accounts;
import com.project.import_tool.service.AccountService;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CustomReader implements ItemStreamReader<Accounts> {

    private CSVReader csvReader;
    private final List<String[]> failedRecords = new ArrayList<>();

    @Autowired
    private AccountService accountService;

    private boolean initialized = false;

    @Override
    public Accounts read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(!initialized){
            Reader reader = new FileReader(System.getProperty("user.dir") + "/uploads/accounts.csv");
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
//                    .withIgnoreQuotations(true)
                    .build();

            this.csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            initialized = true;
        }

        String[] line;
        while((line = csvReader.readNext()) != null){
            System.out.println(line);
            if (isValid(line)) {
                return convertToAccount(line);
            } else {
                failedRecords.add(line);
            }
        }
        return null;
    }

    private Accounts convertToAccount(String[] line) {
        Accounts accounts = new Accounts();
        accounts.setAccountName(line[0]);
        accounts.setAccountType(line[1]);
        accounts.setIndustry(line[2]);
        return accounts;
    }

    private boolean isValid(String[] line){
        return true;
    }
}
