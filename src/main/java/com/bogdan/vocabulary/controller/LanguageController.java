package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.model.Language;
import com.bogdan.vocabulary.service.language.LanguageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageServiceImpl languageService;

    @Autowired
    public LanguageController(LanguageServiceImpl languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        return languages != null && !languages.isEmpty()
                ? new ResponseEntity<>(languages, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
