package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyNotFoundException;
import com.bogdan.vocabulary.mapper.DictionaryMapper;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.repository.DictionaryRepository;
import com.bogdan.vocabulary.service.language.LanguageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    private final LanguageServiceImpl languageService;

    private static final String DICTIONARY_NOT_FOUND = "Dictionary with id = '%d' not found.";

    @Override
    public DictionaryDto createDictionary(DictionaryDto dictionaryDto) {
        checkConflict(dictionaryDto);
        Dictionary dictionary = DictionaryMapper.mapToDictionary(dictionaryDto);
        Dictionary savedDictionary = dictionaryRepository.save(dictionary);
        return DictionaryMapper.mapToDictionaryDto(savedDictionary);
    }

    @Override
    public List<DictionaryDto> getAllDictionaries() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        return dictionaries.isEmpty()
                ? new ArrayList<>()
                : dictionaries.stream().map(DictionaryMapper::mapToDictionaryDto).collect(Collectors.toList());
    }

    @Override
    public DictionaryDto getDictionary(Long id) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(DICTIONARY_NOT_FOUND, id));
        }

        return DictionaryMapper.mapToDictionaryDto(optionalDictionary.get());
    }

    @Override
    public DictionaryDto patchDictionary(Long id, Map<String, Object> changes) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(DICTIONARY_NOT_FOUND, id));
        }

        Dictionary savedDictionary = optionalDictionary.get();

        changes.forEach((change, value) -> {
            switch (change) {
                case "nativeLanguageId" -> savedDictionary.setNativeLanguageId(UUID.fromString(value.toString()));
                case "learnLanguageId" -> savedDictionary.setLearnLanguageId(UUID.fromString(value.toString()));
                case "dictionaryName" -> savedDictionary.setDictionaryName(value.toString());
            }
        });

        dictionaryRepository.save(savedDictionary);
        DictionaryDto dictionaryDto = DictionaryMapper.mapToDictionaryDto(savedDictionary);
        checkConflict(dictionaryDto);
        return dictionaryDto;
    }

    @Override
    public void deleteDictionary(Long id) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(DICTIONARY_NOT_FOUND, id));
        }

        dictionaryRepository.deleteById(id);
    }

    private void checkConflict(DictionaryDto dictionaryDto) {
        LanguageDto nativeLanguage = languageService.getLanguage(UUID.fromString(dictionaryDto.getNativeLanguageId()));
        LanguageDto learnLanguage = languageService.getLanguage(UUID.fromString(dictionaryDto.getLearnLanguageId()));

        if (nativeLanguage.getLanguageId() == learnLanguage.getLanguageId()) {
            throw new VocabularyBusinessException("You can't create dictionary with the same languages.");
        }
    }
}
