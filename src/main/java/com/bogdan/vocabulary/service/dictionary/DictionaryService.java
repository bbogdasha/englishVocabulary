package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.model.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DictionaryService {

    List<DictionaryDto> getAllDictionaries();

    DictionaryDto createDictionary(DictionaryDto dictionary);

    DictionaryDto patchDictionary(Long id, Map<String, Object> changes);

    void deleteDictionary(Long id);
}
