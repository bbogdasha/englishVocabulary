package com.bogdan.vocabulary.exception.dict_lang;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorMessage(int statusCode, LocalDateTime timestamp, Map<String, Object> messages, String solution, String webUrl) {

}
