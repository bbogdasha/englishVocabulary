package com.bogdan.vocabulary.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorMessage(int statusCode, LocalDateTime timestamp, Map<String, Object> messages, String solution, String webUrl) {

}
