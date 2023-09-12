package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyNotFoundException;
import com.bogdan.vocabulary.mapper.LanguageMapper;
import com.bogdan.vocabulary.model.Language;
import com.bogdan.vocabulary.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageDto> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        return languages.isEmpty()
                ? new ArrayList<>()
                : languages.stream().map(LanguageMapper::mapToLanguageDto).collect(Collectors.toList());
    }

    @Override
    public LanguageDto getLanguage(UUID id) {
        Language language = languageRepository.findByLanguageId(id);

        if (language == null) {
            throw new VocabularyNotFoundException("Language with 'uuid = " + id + "' not found.");
        }

        return LanguageMapper.mapToLanguageDto(language);
    }
}
