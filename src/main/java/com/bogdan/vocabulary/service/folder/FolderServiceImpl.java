package com.bogdan.vocabulary.service.folder;

import com.bogdan.vocabulary.converter.FolderConverter;
import com.bogdan.vocabulary.converter.VocabularyConverter;
import com.bogdan.vocabulary.dto.FolderDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.exception.generalException.VocabularyValidationException;
import com.bogdan.vocabulary.model.Folder;
import com.bogdan.vocabulary.model.FolderUpdateRequest;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.Vocabulary;
import com.bogdan.vocabulary.repository.FolderRepository;
import com.bogdan.vocabulary.service.vocabulary.VocabularyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    private final VocabularyServiceImpl vocabularyService;

    private final FolderConverter folderConverter;

    private final VocabularyConverter vocabularyConverter;

    private static final String FOLDER_NOT_FOUND = "Folder [id = %d] not found.";

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<FolderDto> getAllFoldersByVocabulary(Long vocabularyId, PageSettings pageSettings, String filter) {
        vocabularyService.getVocabulary(vocabularyId);

        Sort folderSort = pageSettings.buildSort();
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), folderSort);
        Page<Folder> folderPagePage = folderRepository.findAllFoldersByVocabularyId(vocabularyId, pageRequest, filter);

        return new PageSettingsDto<>(
                folderPagePage.getContent().stream().map(folderConverter::convertToDto).toList(),
                folderPagePage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public FolderDto getFolderByVocabulary(Long vocabularyId, Long folderId) {
        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyService.getVocabulary(vocabularyId));
        Optional<Folder> optionalFolder = vocabulary.getFolders().stream().filter(f -> f.getFolderId() == folderId.intValue()).findFirst();

        if (optionalFolder.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(FOLDER_NOT_FOUND, folderId));
        }

        return folderConverter.convertToDto(optionalFolder.get());
    }

    @Override
    @Transactional
    public FolderDto createFolderInVocabulary(Long vocabularyId, FolderDto folderDto) {
        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyService.getVocabulary(vocabularyId));

        Folder folder = folderConverter.convertToEntity(folderDto);
        folder.setVocabulary(vocabulary);
        folder.setWords(new ArrayList<>());
        folder.setCreatedAt(LocalDateTime.now());
        Folder savedFolder = folderRepository.save(folder);

        return folderConverter.convertToDto(savedFolder);
    }

    @Override
    @Transactional
    public FolderDto patchFolderInVocabulary(Long vocabularyId, Long folderId, FolderUpdateRequest request) {
        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyService.getVocabulary(vocabularyId));
        Optional<Folder> optionalFolder = vocabulary.getFolders().stream().filter(f -> f.getFolderId() == folderId.intValue()).findFirst();

        if (optionalFolder.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(FOLDER_NOT_FOUND, folderId));
        }

        Folder folder = optionalFolder.get();
        boolean changes = false;

        if (request.folderName() != null && !request.folderName().equals(folder.getFolderName())) {
            folder.setFolderName(request.folderName());
            changes = true;
        }

        if (request.description() != null && !request.description().equals(folder.getDescription())) {
            folder.setDescription(request.description());
            changes = true;
        }

        if (!changes) {
            throw new VocabularyValidationException("No data changes found");
        }

        folderRepository.save(folder);
        return folderConverter.convertToDto(folder);
    }

    @Override
    @Transactional
    public void deleteFolderInVocabulary(Long vocabularyId, Long folderId) {
        Vocabulary vocabulary = vocabularyConverter.convertToEntity(vocabularyService.getVocabulary(vocabularyId));
        Optional<Folder> folder = vocabulary.getFolders().stream().filter(f -> f.getFolderId() == folderId.intValue()).findFirst();

        if (folder.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(FOLDER_NOT_FOUND, folderId));
        }

        vocabulary.getFolders().remove(folder.get());
        folderRepository.deleteById(folderId);
    }
}
