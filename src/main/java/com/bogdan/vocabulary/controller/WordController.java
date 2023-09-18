package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.service.word.WordServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
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
                                                     @Valid @RequestBody List<WordDto> wordsDto) {
        List<WordDto> createdWords = wordService.createWords(dictionaryId, wordsDto);
        return new ResponseEntity<>(createdWords, HttpStatus.CREATED);
    }

    @GetMapping("/words")
    public ResponseEntity<DictionaryDto> getAllDictionaryWords(@PathVariable Long dictionaryId) {
        DictionaryDto dictionaryDto = wordService.getAllWordsByDictionaryId(dictionaryId);
        return !dictionaryDto.getWords().isEmpty()
                ? new ResponseEntity<>(dictionaryDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/words/{wordId}")
    public ResponseEntity<WordDto> patchWord(@PathVariable Long dictionaryId, @PathVariable Long wordId,
                                                         @RequestBody Map<String, Object> changes) {
        WordDto wordDto = wordService.patchWord(dictionaryId, wordId, changes);
        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @DeleteMapping("/words/{wordId}")
    public ResponseEntity<String> deleteWord(@PathVariable Long dictionaryId, @PathVariable Long wordId) {
        wordService.deleteWord(dictionaryId, wordId);
        return new ResponseEntity<>("Word successfully deleted!", HttpStatus.OK);
    }
}
