package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.WordFilter;
import com.bogdan.vocabulary.model.WordUpdateRequest;

import java.util.List;

public interface WordService {

    PageSettingsDto<WordDto> getAllWordsByVocabularyAndFolder(
            Long vocabularyId, Long folderId, PageSettings pageSettings, WordFilter filter);

    WordDto getWordByVocabularyAndFolder(Long vocabularyId, Long folderId, Long wordId);

    List<WordDto> createWordsInFolder(Long vocabularyId, Long folderId, List<WordDto> wordsDto);

    WordDto patchWordByVocabularyAndFolder(Long vocabularyId, Long folderId, Long wordId, WordUpdateRequest request);

    void deleteWordInFolder(Long vocabularyId, Long folderId, Long wordId);
}
