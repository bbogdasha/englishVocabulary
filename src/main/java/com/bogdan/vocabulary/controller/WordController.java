package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.service.word.WordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionaries/{dictionaryId}")
public class WordController {

    private final WordServiceImpl wordService;

    @Autowired
    public WordController(WordServiceImpl wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/words")
    public ResponseEntity<List<WordDto>> createWords(@PathVariable Long dictionaryId,
                                                     @RequestBody List<WordDto> wordsDto) {
        List<WordDto> createdWords = wordService.createWords(dictionaryId, wordsDto);
        return new ResponseEntity<>(createdWords, HttpStatus.CREATED);
    }

    @GetMapping("/words")
    public ResponseEntity<DictionaryDto> getAllDictionaryWords(@PathVariable Long dictionaryId) {
        DictionaryDto dictionaryDto = wordService.getAllWordsByDictionaryId(dictionaryId);
        return new ResponseEntity<>(dictionaryDto, HttpStatus.OK);
    }

    @DeleteMapping("/words/{wordId}")
    public ResponseEntity<String> deleteWord(@PathVariable Long dictionaryId, @PathVariable Long wordId) {
        wordService.deleteWord(dictionaryId, wordId);
        return new ResponseEntity<>("Word successfully deleted!", HttpStatus.OK);
    }
}
