package com.bogdan.vocabulary.mapper;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.model.Dictionary;

public class DictionaryMapper {

    public static DictionaryDto mapToDictionaryDto(Dictionary dictionary) {
        DictionaryDto dictionaryDto = new DictionaryDto(
                dictionary.getDictionaryId(),
                dictionary.getNativeLanguageId(),
                dictionary.getLearnLanguageId(),
                dictionary.getDictionaryName()
        );
        return dictionaryDto;
    }

    public static Dictionary mapToDictionary(DictionaryDto dictionaryDto) {
        Dictionary dictionary = new Dictionary(
                dictionaryDto.getDictionaryId(),
                dictionaryDto.getNativeLanguageId(),
                dictionaryDto.getLearnLanguageId(),
                dictionaryDto.getDictionaryName()
        );
        return dictionary;
    }
}
