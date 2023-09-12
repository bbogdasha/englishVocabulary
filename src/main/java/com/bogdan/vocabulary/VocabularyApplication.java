package com.bogdan.vocabulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VocabularyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocabularyApplication.class, args);
	}

	//todo: 1) Rename exception handler to more common
	//todo: 2) Dictionary 409 exception: a) English - English, b) cant change language if exists words

}
