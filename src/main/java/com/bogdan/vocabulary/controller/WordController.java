package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.PageSettings;
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
@RequestMapping("/api/v1/vocabularies/{vocabularyId}")
public class WordController {

    private final WordServiceImpl wordService;

    @Autowired
    public WordController(WordServiceImpl wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/words")
    public ResponseEntity<List<WordDto>> createWords(@PathVariable Long vocabularyId,
                                                     @Valid @RequestBody List<WordDto> wordsDto) {
        List<WordDto> createdWords = wordService.createWords(vocabularyId, wordsDto);
        return new ResponseEntity<>(createdWords, HttpStatus.CREATED);
    }

    @GetMapping("/words")
    public ResponseEntity<PageSettingsDto<WordDto>> getAllVocabularyWords(@PathVariable Long vocabularyId, PageSettings pageSettings) {
        PageSettingsDto<WordDto> wordsDto = wordService.getAllWordsByVocabularyId(vocabularyId, pageSettings);

        return !wordsDto.getContent().isEmpty()
                ? new ResponseEntity<>(wordsDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/words/{wordId}")
    public ResponseEntity<WordDto> patchWord(@PathVariable Long vocabularyId, @PathVariable Long wordId,
                                                         @RequestBody Map<String, Object> changes) {
        WordDto wordDto = wordService.patchWord(vocabularyId, wordId, changes);
        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @DeleteMapping("/words/{wordId}")
    public ResponseEntity<String> deleteWord(@PathVariable Long vocabularyId, @PathVariable Long wordId) {
        wordService.deleteWord(vocabularyId, wordId);
        return new ResponseEntity<>("Word successfully deleted!", HttpStatus.OK);
    }
}
