package com.bogdan.vocabulary.service.folder;

import com.bogdan.vocabulary.dto.FolderDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.model.FolderUpdateRequest;
import com.bogdan.vocabulary.model.PageSettings;

public interface FolderService {

    PageSettingsDto<FolderDto> getAllFoldersByVocabulary(Long vocabularyId, PageSettings pageSettings, String filter);

    FolderDto getFolderByVocabulary(Long vocabularyId, Long folderId);

    FolderDto createFolderInVocabulary(Long vocabularyId, FolderDto folderDto);

    FolderDto patchFolderInVocabulary(Long vocabularyId, Long folderId, FolderUpdateRequest request);

    void deleteFolderInVocabulary(Long vocabularyId, Long folderId);

}
