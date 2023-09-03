package com.bogdan.vocabulary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VocabularyController {

    @GetMapping("/")
    public String index() {
        return "Hello world!";
    }
}
