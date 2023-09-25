package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.View;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.service.dictionary.DictionaryServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController {

    private final DictionaryServiceImpl dictionaryService;

    @Autowired
    public DictionaryController(DictionaryServiceImpl dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping
    public ResponseEntity<DictionaryDto> createDictionary(@Valid @RequestBody DictionaryDto dictionaryDto) {
        DictionaryDto createdDictionary = dictionaryService.createDictionary(dictionaryDto);
        return new ResponseEntity<>(createdDictionary, HttpStatus.CREATED);
    }

    @GetMapping
    @JsonView(value = View.SummaryDictionary.class)
    public ResponseEntity<PageSettingsDto<DictionaryDto>> getAllDictionaries(@Valid PageSettings pageSettings) {
        PageSettingsDto<DictionaryDto> dictionariesDto = dictionaryService.getAllDictionaries(pageSettings);

        return !dictionariesDto.getContent().isEmpty()
                ? new ResponseEntity<>(dictionariesDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{dictionaryId}")
    public ResponseEntity<DictionaryDto> patchDictionary(@PathVariable Long dictionaryId, @RequestBody Map<String, Object> changes) {
        DictionaryDto patchedDictionary = dictionaryService.patchDictionary(dictionaryId, changes);
        return new ResponseEntity<>(patchedDictionary, HttpStatus.OK);
    }

    @DeleteMapping("/{dictionaryId}")
    public ResponseEntity<String> deleteDictionary(@PathVariable Long dictionaryId) {
        dictionaryService.deleteDictionary(dictionaryId);
        return new ResponseEntity<>("Dictionary successfully deleted!", HttpStatus.OK);
    }
}
