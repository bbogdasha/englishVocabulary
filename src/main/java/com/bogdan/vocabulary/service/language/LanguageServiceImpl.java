package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.converter.LanguageConverter;
import com.bogdan.vocabulary.model.Language;
import com.bogdan.vocabulary.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    private final LanguageConverter languageConverter;

    private static final String LANGUAGE_NOT_FOUND = "Language [uuid = %s] not found.";

    @Override
    @Transactional(readOnly = true)
    public List<LanguageDto> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        return languages.isEmpty()
                ? new ArrayList<>()
                : languages.stream().map(languageConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LanguageDto getLanguage(UUID id) {
        Optional<Language> language = languageRepository.findByLanguageId(id);

        if (language.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(LANGUAGE_NOT_FOUND, id));
        }

        return languageConverter.convertToDto(language.get());
    }
}
