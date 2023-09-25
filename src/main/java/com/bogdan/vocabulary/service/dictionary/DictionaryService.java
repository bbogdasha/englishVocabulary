package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.model.PageSettings;

import java.util.Map;

public interface DictionaryService {

    PageSettingsDto<DictionaryDto> getAllDictionaries(PageSettings pageSettings);

    DictionaryDto getDictionary(Long id);

    DictionaryDto createDictionary(DictionaryDto dictionary);

    DictionaryDto patchDictionary(Long id, Map<String, Object> changes);

    void deleteDictionary(Long id);
}
