package com.bogdan.vocabulary.exception.dict_lang;

public class VocabularyNotFoundException extends RuntimeException {

    public VocabularyNotFoundException() {
    }

    public VocabularyNotFoundException(String message) {
        super(message);
    }
}
