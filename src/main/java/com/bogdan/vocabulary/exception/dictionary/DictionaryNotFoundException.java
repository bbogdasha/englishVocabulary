package com.bogdan.vocabulary.exception.dictionary;

public class DictionaryNotFoundException extends RuntimeException {

    public DictionaryNotFoundException() {
    }

    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
