package com.bogdan.vocabulary.mapper;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.model.Dictionary;

import java.util.UUID;

public class DictionaryMapper {

    public static DictionaryDto mapToDictionaryDto(Dictionary dictionary) {
        DictionaryDto dictionaryDto = new DictionaryDto(
                dictionary.getDictionaryId(),
                dictionary.getNativeLanguageId().toString(),
                dictionary.getLearnLanguageId().toString(),
                dictionary.getDictionaryName()
        );
        return dictionaryDto;
    }

    public static Dictionary mapToDictionary(DictionaryDto dictionaryDto) {
        Dictionary dictionary = new Dictionary(
                dictionaryDto.getDictionaryId(),
                UUID.fromString(dictionaryDto.getNativeLanguageId()),
                UUID.fromString(dictionaryDto.getLearnLanguageId()),
                dictionaryDto.getDictionaryName()
        );
        return dictionary;
    }
}
