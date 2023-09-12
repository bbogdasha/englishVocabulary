package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.service.dictionary.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

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
        DictionaryDto savedDictionary = dictionaryService.createDictionary(dictionaryDto);
        return new ResponseEntity<>(savedDictionary, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DictionaryDto>> getAllDictionaries() {
        List<DictionaryDto> dictionariesDto = dictionaryService.getAllDictionaries();
        return !dictionariesDto.isEmpty()
                ? new ResponseEntity<>(dictionariesDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DictionaryDto> patchDictionary(@PathVariable Long id, @RequestBody Map<String, Object> changes) {
        DictionaryDto patchedDictionary = dictionaryService.patchDictionary(id, changes);
        return new ResponseEntity<>(patchedDictionary, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDictionary(@PathVariable Long id) {
        dictionaryService.deleteDictionary(id);
        return new ResponseEntity<>("Dictionary successfully deleted!", HttpStatus.OK);
    }
}
