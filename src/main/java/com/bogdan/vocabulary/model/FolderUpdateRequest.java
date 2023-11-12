package com.bogdan.vocabulary.model;

public record FolderUpdateRequest(
        String folderName,
        String description
) {
}
