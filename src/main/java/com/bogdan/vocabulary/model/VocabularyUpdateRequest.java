package com.bogdan.vocabulary.model;

import java.util.UUID;

public record VocabularyUpdateRequest(
        UUID nativeLanguageId,
        UUID learnLanguageId,
        String vocabularyName,
        String description
) {
}
