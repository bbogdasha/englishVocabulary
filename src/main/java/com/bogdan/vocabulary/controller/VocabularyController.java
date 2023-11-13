package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.View;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.VocabularyUpdateRequest;
import com.bogdan.vocabulary.service.vocabulary.VocabularyServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/vocabularies")
public class VocabularyController {

    private final VocabularyServiceImpl vocabularyService;

    @Autowired
    public VocabularyController(VocabularyServiceImpl vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @PostMapping
    public ResponseEntity<VocabularyDto> createVocabulary(@Valid @RequestBody VocabularyDto vocabularyDto) {
        VocabularyDto createdVocabulary = vocabularyService.createVocabulary(vocabularyDto);
        return new ResponseEntity<>(createdVocabulary, HttpStatus.CREATED);
    }

    @GetMapping
    @JsonView(value = View.SummaryVocabulary.class)
    public ResponseEntity<PageSettingsDto<VocabularyDto>> getAllVocabularies(
            @Valid PageSettings pageSettings, @RequestParam(required = false, name = "vocabularyName",
            defaultValue = "") String vocabularyName) {
        PageSettingsDto<VocabularyDto> vocabulariesDto = vocabularyService.getAllVocabularies(pageSettings, vocabularyName);

        return new ResponseEntity<>(vocabulariesDto, HttpStatus.OK);
    }

    @PatchMapping("/{vocabularyId}")
    public ResponseEntity<VocabularyDto> patchVocabulary(@PathVariable Long vocabularyId,
                                                         @RequestBody VocabularyUpdateRequest request) {
        VocabularyDto patchVocabulary = vocabularyService.patchVocabulary(vocabularyId, request);
        return new ResponseEntity<>(patchVocabulary, HttpStatus.OK);
    }

    @DeleteMapping("/{vocabularyId}")
    public ResponseEntity<String> deleteVocabulary(@PathVariable Long vocabularyId) {
        vocabularyService.deleteVocabulary(vocabularyId);
        return new ResponseEntity<>("Vocabulary successfully deleted!", HttpStatus.OK);
    }
}
