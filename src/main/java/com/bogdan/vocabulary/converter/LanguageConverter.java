package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.model.Language;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LanguageConverter {

    private final ModelMapper modelMapper;

    public LanguageConverter() {
        this.modelMapper = new ModelMapper();
    }

    public LanguageDto convertToDto(Language language) {
        return modelMapper.map(language, LanguageDto.class);
    }

    public Language convertToEntity(LanguageDto languageDto) {
        return modelMapper.map(languageDto, Language.class);
    }

}
