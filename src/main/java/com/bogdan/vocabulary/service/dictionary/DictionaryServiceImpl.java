package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.converter.DictionaryConverter;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.repository.DictionaryRepository;
import com.bogdan.vocabulary.service.language.LanguageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    private final LanguageServiceImpl languageService;

    private final DictionaryConverter dictionaryConverter;

    private static final String DICTIONARY_NOT_FOUND = "Dictionary with 'id = %d' not found.";

    @Override
    @Transactional
    public DictionaryDto createDictionary(DictionaryDto dictionaryDto) {
        checkConflict(dictionaryDto);

        Dictionary dictionary = dictionaryConverter.convertToEntity(dictionaryDto);
        dictionary.setWords(new ArrayList<>());
        dictionary.setCreatedAt(LocalDateTime.now());
        Dictionary savedDictionary = dictionaryRepository.save(dictionary);

        return dictionaryConverter.convertToDto(savedDictionary);
    }

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<DictionaryDto> getAllDictionaries(PageSettings pageSettings) {

        Sort dictionarySort = pageSettings.buildSort();
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), dictionarySort);
        Page<Dictionary> dictionaryPage = dictionaryRepository.findAll(pageRequest);

        return new PageSettingsDto<>(
                dictionaryPage.getContent().stream().map(dictionaryConverter::convertToDto).toList(),
                dictionaryPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryDto getDictionary(Long id) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(DICTIONARY_NOT_FOUND, id));
        }

        return dictionaryConverter.convertToDto(optionalDictionary.get());
    }

    @Override
    @Transactional
    public DictionaryDto patchDictionary(Long id, Map<String, Object> changes) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(DICTIONARY_NOT_FOUND, id));
        }

        Dictionary savedDictionary = optionalDictionary.get();

        if (!savedDictionary.getWords().isEmpty() &&
                (changes.containsKey("nativeLanguageId") || changes.containsKey("learnLanguageId"))) {
            throw new VocabularyBusinessException("You cannot change languages if there are already words in the dictionary.");
        }

        changes.forEach((change, value) -> {
            switch (change) {
                case "nativeLanguageId" -> savedDictionary.setNativeLanguageId(UUID.fromString(value.toString()));
                case "learnLanguageId" -> savedDictionary.setLearnLanguageId(UUID.fromString(value.toString()));
                case "dictionaryName" -> savedDictionary.setDictionaryName(value.toString());
            }
        });

        DictionaryDto dictionaryDto = dictionaryConverter.convertToDto(savedDictionary);
        checkConflict(dictionaryDto);

        dictionaryRepository.save(savedDictionary);

        return dictionaryDto;
    }

    @Override
    @Transactional
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
            throw new VocabularyBusinessException("You can't create/update dictionary with the same languages.");
        }
    }
}
