package com.bogdan.vocabulary.service.vocabulary;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.converter.VocabularyConverter;
import com.bogdan.vocabulary.model.Vocabulary;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.repository.VocabularyRepository;
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
public class VocabularyServiceImpl implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;

    private final LanguageServiceImpl languageService;

    private final VocabularyConverter vocabularyConverter;

    private static final String VOCABULARY_NOT_FOUND = "Vocabulary with 'id = %d' not found.";

    @Override
    @Transactional
    public VocabularyDto createVocabulary(VocabularyDto vocabularyDto) {
        checkConflict(vocabularyDto);

        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyDto);
        vocabulary.setWords(new ArrayList<>());
        vocabulary.setCreatedAt(LocalDateTime.now());
        Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

        return vocabularyConverter.convertToDto(savedVocabulary);
    }

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<VocabularyDto> getAllVocabularies(PageSettings pageSettings) {

        Sort vocabularySort = pageSettings.buildSort();
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), vocabularySort);
        Page<Vocabulary> vocabularyPage = vocabularyRepository.findAll(pageRequest);

        return new PageSettingsDto<>(
                vocabularyPage.getContent().stream().map(vocabularyConverter::convertToDto).toList(),
                vocabularyPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public VocabularyDto getVocabulary(Long id) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(id);

        if (optionalVocabulary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(VOCABULARY_NOT_FOUND, id));
        }

        return vocabularyConverter.convertToDto(optionalVocabulary.get());
    }

    @Override
    @Transactional
    public VocabularyDto patchVocabulary(Long id, Map<String, Object> changes) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(id);

        if (optionalVocabulary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(VOCABULARY_NOT_FOUND, id));
        }

        Vocabulary savedVocabulary = optionalVocabulary.get();

        if (!savedVocabulary.getWords().isEmpty() &&
                (changes.containsKey("nativeLanguageId") || changes.containsKey("learnLanguageId"))) {
            throw new VocabularyBusinessException("You cannot change languages if there are already words in the vocabulary.");
        }

        changes.forEach((change, value) -> {
            switch (change) {
                case "nativeLanguageId" -> savedVocabulary.setNativeLanguageId(UUID.fromString(value.toString()));
                case "learnLanguageId" -> savedVocabulary.setLearnLanguageId(UUID.fromString(value.toString()));
                case "vocabularyName" -> savedVocabulary.setVocabularyName(value.toString());
            }
        });

        VocabularyDto vocabularyDto = vocabularyConverter.convertToDto(savedVocabulary);
        checkConflict(vocabularyDto);

        vocabularyRepository.save(savedVocabulary);

        return vocabularyDto;
    }

    @Override
    @Transactional
    public void deleteVocabulary(Long id) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(id);

        if (optionalVocabulary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(VOCABULARY_NOT_FOUND, id));
        }

        vocabularyRepository.deleteById(id);
    }

    private void checkConflict(VocabularyDto vocabularyDto) {
        LanguageDto nativeLanguage = languageService.getLanguage(UUID.fromString(vocabularyDto.getNativeLanguageId()));
        LanguageDto learnLanguage = languageService.getLanguage(UUID.fromString(vocabularyDto.getLearnLanguageId()));

        if (nativeLanguage.getLanguageId() == learnLanguage.getLanguageId()) {
            throw new VocabularyBusinessException("You can't create/update vocabulary with the same languages.");
        }
    }
}
