package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.dto.LanguageDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageService {

    List<LanguageDto> getAllLanguages();

    LanguageDto getLanguage(UUID id);
}
