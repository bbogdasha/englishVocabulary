package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.PageSettings;

import java.util.List;
import java.util.Map;

public interface WordService {

    PageSettingsDto<WordDto> getAllWordsByVocabularyId(Long vocabularyId, PageSettings pageSettings);

    WordDto getWordById(Long vocabularyId, Long wordId);

    List<WordDto> createWords(Long vocabularyId, List<WordDto> wordsDto);

    WordDto patchWord(Long vocabularyId, Long wordId, Map<String, Object> changes);

    void deleteWord(Long vocabularyId, Long wordId);
}
