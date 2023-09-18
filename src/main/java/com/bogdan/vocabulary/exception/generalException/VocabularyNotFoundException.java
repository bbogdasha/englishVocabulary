package com.bogdan.vocabulary.exception.generalException;

public class VocabularyNotFoundException extends RuntimeException {

    public VocabularyNotFoundException() {
    }

    public VocabularyNotFoundException(String message) {
        super(message);
    }
}
