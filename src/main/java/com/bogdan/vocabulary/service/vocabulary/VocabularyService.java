package com.bogdan.vocabulary.service.vocabulary;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.model.PageSettings;

import java.util.Map;

public interface VocabularyService {

    PageSettingsDto<VocabularyDto> getAllVocabularies(PageSettings pageSettings);

    VocabularyDto getVocabulary(Long id);

    VocabularyDto createVocabulary(VocabularyDto vocabularyDto);

    VocabularyDto patchVocabulary(Long id, Map<String, Object> changes);

    void deleteVocabulary(Long id);
}
