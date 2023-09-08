package com.bogdan.vocabulary.mapper;

import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.model.Language;

public class LanguageMapper {

    public static LanguageDto mapToLanguageDto(Language language) {
        LanguageDto languageDto = new LanguageDto(
                language.getLanguageId(),
                language.getLanguageName()
        );
        return languageDto;
    }

    public static Language mapToLanguage(LanguageDto languageDto) {
        Language language = new Language(
                languageDto.getLanguageId(),
                languageDto.getLanguageName()
        );
        return language;
    }
}
