package com.bogdan.vocabulary.service.language;

import com.bogdan.vocabulary.model.Language;
import com.bogdan.vocabulary.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        return languages.isEmpty() ? new ArrayList<>() : languages;
    }
}
