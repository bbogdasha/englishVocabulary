package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    List<DictionaryDto> getAllDictionaries();

    DictionaryDto getDictionary(Long id);

    DictionaryDto createDictionary(DictionaryDto dictionary);

    DictionaryDto patchDictionary(Long id, Map<String, Object> changes);

    void deleteDictionary(Long id);
}
