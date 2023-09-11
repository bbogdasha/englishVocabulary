package com.bogdan.vocabulary.exception.dictionary;

public class DictionaryValidationException extends RuntimeException {

    public DictionaryValidationException() {
    }

    public DictionaryValidationException(String message) {
        super(message);
    }
}
