package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.View;
import com.bogdan.vocabulary.service.dictionary.DictionaryServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
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
    public ResponseEntity<List<DictionaryDto>> getAllDictionaries() {
        List<DictionaryDto> dictionariesDto = dictionaryService.getAllDictionaries();
        return !dictionariesDto.isEmpty()
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
