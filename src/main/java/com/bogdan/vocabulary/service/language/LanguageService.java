package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.dto.LanguageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageService {

    List<LanguageDto> getAllLanguages();
}
