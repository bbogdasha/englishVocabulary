package com.bogdan.vocabulary.model;

public record WordUpdateRequest(
        String word,
        String translation,
        String example
) {
}
