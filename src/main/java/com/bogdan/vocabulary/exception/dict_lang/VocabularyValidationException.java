package com.bogdan.vocabulary.exception.dict_lang;

public class VocabularyValidationException extends RuntimeException {

    public VocabularyValidationException() {
    }

    public VocabularyValidationException(String message) {
        super(message);
    }
}
