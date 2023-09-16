package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.dto.LanguageDto;

import java.util.List;
import java.util.UUID;

public interface LanguageService {

    List<LanguageDto> getAllLanguages();

    LanguageDto getLanguage(UUID id);
}
