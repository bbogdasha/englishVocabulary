package com.bogdan.vocabulary.exception;

import com.bogdan.vocabulary.exception.dict_lang.ErrorMessage;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyNotFoundException;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHand extends ResponseEntityExceptionHandler {

    private final Map<String, Object> messages = new HashMap<>();

    @ExceptionHandler(VocabularyNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage exception(VocabularyNotFoundException exception, WebRequest request) {
        return notFoundException(exception, request);
    }

    @ExceptionHandler(VocabularyValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage exception(VocabularyValidationException exception, WebRequest request) {
        return validationException(exception, request);
    }

    @ExceptionHandler(VocabularyBusinessException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage exception(VocabularyBusinessException exception, WebRequest request) {
        return conflictException(exception, request);
    }

    private ErrorMessage notFoundException(VocabularyNotFoundException exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                messages,
                "Please make sure the data you are looking for exists.",
                request.getDescription(false));
    }

    private ErrorMessage validationException(VocabularyValidationException exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                messages,
                "Please verify the data entered.",
                request.getDescription(false));
    }

    private ErrorMessage conflictException(VocabularyBusinessException exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                messages,
                "Please verify the data entered.",
                request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorMessage globalExceptionHandler(Exception exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                messages,
                "Sorry, something went wrong :(",
                request.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> fields = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fields.put(fieldName, message);
        });

        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                LocalDateTime.now(),
                fields,
                "Please verify the data entered.",
                request.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
