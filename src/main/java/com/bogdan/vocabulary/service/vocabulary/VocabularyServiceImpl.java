package com.bogdan.vocabulary.service.vocabulary;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.dto.LanguageDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.converter.VocabularyConverter;
import com.bogdan.vocabulary.exception.generalException.VocabularyValidationException;
import com.bogdan.vocabulary.model.Vocabulary;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.VocabularyUpdateRequest;
import com.bogdan.vocabulary.repository.VocabularyRepository;
import com.bogdan.vocabulary.service.PageSettingsService;
import com.bogdan.vocabulary.service.language.LanguageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class VocabularyServiceImpl extends PageSettingsService implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;

    private final LanguageServiceImpl languageService;

    private final VocabularyConverter vocabularyConverter;

    private static final String VOCABULARY_NOT_FOUND = "Vocabulary [id = %d] not found.";

    private static final List<String> SORT_COLUMN = Arrays.asList("created_at", "vocabulary_name", "description");

    @Override
    @Transactional
    public VocabularyDto createVocabulary(VocabularyDto vocabularyDto) {
        checkConflict(vocabularyDto);

        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyDto);
        vocabulary.setFolders(new ArrayList<>());
        vocabulary.setVocabularyName(vocabulary.getVocabularyName().trim());
        Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

        return vocabularyConverter.convertToDto(savedVocabulary);
    }

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<VocabularyDto> getAllVocabularies(PageSettings pageSettings, String filter) {

        Sort vocabularySort = buildSort(SORT_COLUMN, pageSettings);
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), vocabularySort);
        Page<Vocabulary> vocabularyPage = vocabularyRepository.findAllVocabularies(pageRequest, filter);

        return new PageSettingsDto<>(
                vocabularyPage.getContent().stream().map(vocabularyConverter::convertToDto).toList(),
                vocabularyPage.getTotalElements(),
                vocabularyPage.getNumber(),
                vocabularyPage.getNumberOfElements()
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
    public VocabularyDto patchVocabulary(Long id, VocabularyUpdateRequest request) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(id);

        if (optionalVocabulary.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(VOCABULARY_NOT_FOUND, id));
        }

        Vocabulary vocabulary = optionalVocabulary.get();

        if (!vocabulary.getFolders().isEmpty() &&
                (request.learnLanguageId() != null || request.nativeLanguageId() != null)) {
            throw new VocabularyBusinessException("You cannot change languages if there are already folders in the vocabulary.");
        }

        boolean changes = false;

        if (request.nativeLanguageId() != null && !request.nativeLanguageId().equals(vocabulary.getNativeLanguageId())) {
            vocabulary.setNativeLanguageId(request.nativeLanguageId());
            changes = true;
        }

        if (request.learnLanguageId() != null && !request.learnLanguageId().equals(vocabulary.getLearnLanguageId())) {
            vocabulary.setLearnLanguageId(request.learnLanguageId());
            changes = true;
        }

        if (request.vocabularyName() != null && !request.vocabularyName().equals(vocabulary.getVocabularyName())) {
            vocabulary.setVocabularyName(request.vocabularyName());
            changes = true;
        }

        if (request.description() != null && !request.description().equals(vocabulary.getDescription())) {
            vocabulary.setDescription(request.description());
            changes = true;
        }

        if (!changes) {
            throw new VocabularyValidationException("No data changes found");
        }

        vocabularyRepository.save(vocabulary);

        VocabularyDto vocabularyDto = vocabularyConverter.convertToDto(vocabulary);
        checkConflict(vocabularyDto);

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
