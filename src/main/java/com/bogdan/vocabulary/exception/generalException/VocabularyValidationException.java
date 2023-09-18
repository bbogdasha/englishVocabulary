package com.bogdan.vocabulary.exception.generalException;

public class VocabularyValidationException extends RuntimeException {

    public VocabularyValidationException() {
    }

    public VocabularyValidationException(String message) {
        super(message);
    }
}
