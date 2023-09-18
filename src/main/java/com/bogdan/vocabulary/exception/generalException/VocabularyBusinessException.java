package com.bogdan.vocabulary.exception.generalException;

public class VocabularyBusinessException extends RuntimeException {

    public VocabularyBusinessException() {
    }

    public VocabularyBusinessException(String message) {
        super(message);
    }
}
