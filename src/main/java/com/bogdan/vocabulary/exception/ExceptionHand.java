package com.bogdan.vocabulary.exception;

import com.bogdan.vocabulary.exception.dictionary.DictionaryValidationException;
import com.bogdan.vocabulary.exception.dictionary.DictionaryNotFoundException;
import com.bogdan.vocabulary.exception.dictionary.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHand extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DictionaryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage exception(DictionaryNotFoundException exception, WebRequest request) {
        return notFoundDictionaryError(exception, request);
    }

    @ExceptionHandler(DictionaryValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage exception(DictionaryValidationException exception, WebRequest request) {
        return createDictionaryError(exception, request);
    }

    private ErrorMessage notFoundDictionaryError(DictionaryNotFoundException exception, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "Please make sure the dictionary you are looking for exists.",
                request.getDescription(false));
    }

    private ErrorMessage createDictionaryError(DictionaryValidationException exception, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "Please verify the data entered.",
                request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorMessage globalExceptionHandler(Exception exception, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                "Please verify the data entered.",
                request.getDescription(false));
    }

}
