package com.bogdan.vocabulary.controller;

import com.bogdan.vocabulary.dto.FolderDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.model.FolderUpdateRequest;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.service.folder.FolderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/vocabularies/{vocabularyId}/folders")
public class FolderController {

    private final FolderServiceImpl folderService;

    @Autowired
    public FolderController(FolderServiceImpl folderService) {
        this.folderService = folderService;
    }

    @GetMapping
    public ResponseEntity<PageSettingsDto<FolderDto>> getAllFolders(
            @PathVariable Long vocabularyId, @Valid PageSettings pageSettings,
            @RequestParam(required = false, name = "folderName", defaultValue = "") String folderName) {

        PageSettingsDto<FolderDto> foldersDto =
                folderService.getAllFoldersByVocabulary(vocabularyId, pageSettings, folderName);

        return new ResponseEntity<>(foldersDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FolderDto> createFolder(@PathVariable Long vocabularyId,
                                                  @Valid @RequestBody FolderDto folderDto) {
        FolderDto createdFolder = folderService.createFolderInVocabulary(vocabularyId, folderDto);
        return new ResponseEntity<>(createdFolder, HttpStatus.CREATED);
    }

    @PatchMapping("/{folderId}")
    public ResponseEntity<FolderDto> patchFolder(@PathVariable Long vocabularyId, @PathVariable Long folderId,
                                                 @RequestBody FolderUpdateRequest request) {
        FolderDto patchedFolder = folderService.patchFolderInVocabulary(vocabularyId, folderId, request);
        return new ResponseEntity<>(patchedFolder, HttpStatus.OK);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<String> deleteFolder(@PathVariable Long vocabularyId, @PathVariable Long folderId) {
        folderService.deleteFolderInVocabulary(vocabularyId, folderId);
        return new ResponseEntity<>("Folder successfully deleted!", HttpStatus.OK);
    }
}
