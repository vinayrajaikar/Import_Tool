package com.project.import_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public class ImportToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportToolApplication.class, args);
	}

}
