package com.bogdan.vocabulary.exception.dictionary;

public class DictionaryBusinessException extends RuntimeException {

    public DictionaryBusinessException() {
    }

    public DictionaryBusinessException(String message) {
        super(message);
    }
}
