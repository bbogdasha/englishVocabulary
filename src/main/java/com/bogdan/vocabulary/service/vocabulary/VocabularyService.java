package com.bogdan.vocabulary.service.vocabulary;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.VocabularyUpdateRequest;

public interface VocabularyService {

    PageSettingsDto<VocabularyDto> getAllVocabularies(PageSettings pageSettings, String filter);

    VocabularyDto getVocabulary(Long id);

    VocabularyDto createVocabulary(VocabularyDto vocabularyDto);

    VocabularyDto patchVocabulary(Long id, VocabularyUpdateRequest request);

    void deleteVocabulary(Long id);
}
