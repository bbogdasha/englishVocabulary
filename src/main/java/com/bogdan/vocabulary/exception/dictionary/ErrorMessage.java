package com.bogdan.vocabulary.exception.dictionary;

import java.time.LocalDateTime;

public record ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String solution, String description) {

}
