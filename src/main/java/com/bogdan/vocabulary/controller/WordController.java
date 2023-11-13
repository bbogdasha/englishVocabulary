package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.WordFilter;
import com.bogdan.vocabulary.model.WordUpdateRequest;
import com.bogdan.vocabulary.service.word.WordServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/vocabularies/{vocabularyId}/folders/{folderId}/words")
public class WordController {

    private final WordServiceImpl wordService;

    @Autowired
    public WordController(WordServiceImpl wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<List<WordDto>> createWords(@PathVariable Long vocabularyId, @PathVariable Long folderId,
                                                     @Valid @RequestBody List<WordDto> wordsDto) {
        List<WordDto> createdWords = wordService.createWordsInFolder(vocabularyId, folderId, wordsDto);
        return new ResponseEntity<>(createdWords, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageSettingsDto<WordDto>> getAllWords(
            @PathVariable Long vocabularyId, @PathVariable Long folderId, PageSettings pageSettings,
            @RequestParam(name = "word", required = false, defaultValue = "") String filterWord,
            @RequestParam(name = "translation", required = false, defaultValue = "") String filterTranslation) {

        WordFilter filter = new WordFilter(filterWord, filterTranslation);

        PageSettingsDto<WordDto> wordsDto =
                wordService.getAllWordsByVocabularyAndFolder(vocabularyId, folderId, pageSettings, filter);

        return new ResponseEntity<>(wordsDto, HttpStatus.OK);
    }

    @PatchMapping("/{wordId}")
    public ResponseEntity<WordDto> patchWord(@PathVariable Long vocabularyId, @PathVariable Long folderId,
                                             @PathVariable Long wordId, @RequestBody WordUpdateRequest request) {
        WordDto wordDto = wordService.patchWordByVocabularyAndFolder(vocabularyId, folderId, wordId, request);
        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @DeleteMapping("/{wordId}")
    public ResponseEntity<String> deleteWord(@PathVariable Long vocabularyId, @PathVariable Long folderId,
                                             @PathVariable Long wordId) {
        wordService.deleteWordInFolder(vocabularyId, folderId, wordId);
        return new ResponseEntity<>("Word successfully deleted!", HttpStatus.OK);
    }
}
