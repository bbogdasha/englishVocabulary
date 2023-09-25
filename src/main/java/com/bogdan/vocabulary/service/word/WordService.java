package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.Word;

import java.util.List;
import java.util.Map;

public interface WordService {

    PageSettingsDto<WordDto> getAllWordsByDictionaryId(Long dictionaryId, PageSettings pageSettings);

    WordDto getWordById(Long dictionaryId, Long wordId);

    List<WordDto> createWords(Long dictionaryId, List<WordDto> wordsDto);

    WordDto patchWord(Long dictionaryId, Long wordId, Map<String, Object> changes);

    void deleteWord(Long dictionaryId, Long wordId);
}
