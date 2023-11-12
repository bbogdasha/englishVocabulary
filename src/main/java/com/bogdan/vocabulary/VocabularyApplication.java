package com.bogdan.vocabulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VocabularyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocabularyApplication.class, args);
	}

	//TODO: checkConflict, optionalVocabulary.isEmpty() -> existsCustomerWithId
	//TODO: view languages in vocabulary
	//TODO: "totalPages": 3, "currentPage": 1
	//TODO: spec queries in repo
	//TODO: refactoring sql queries

}
