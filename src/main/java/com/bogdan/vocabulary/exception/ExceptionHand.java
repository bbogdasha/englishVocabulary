package com.bogdan.vocabulary.exception;

import com.bogdan.vocabulary.exception.generalException.VocabularyBusinessException;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.exception.generalException.VocabularyValidationException;
import jakarta.validation.ConstraintViolationException;
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

    private static final String NOT_EXISTS_DATA = "Please make sure the data you are looking for exists.";

    private static final String WRONG_DATA = "Please verify the data entered.";

    private static final String SERVER_ERROR = "Sorry, something went wrong :(";

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
        return responseException(HttpStatus.NOT_FOUND, messages, NOT_EXISTS_DATA, request);
    }

    private ErrorMessage validationException(VocabularyValidationException exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return responseException(HttpStatus.BAD_REQUEST, messages, WRONG_DATA, request);
    }

    private ErrorMessage conflictException(VocabularyBusinessException exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return responseException(HttpStatus.CONFLICT, messages, WRONG_DATA, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorMessage globalExceptionHandler(Exception exception, WebRequest request) {
        messages.put("message", exception.getMessage());
        return responseException(HttpStatus.INTERNAL_SERVER_ERROR, messages, SERVER_ERROR, request);
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

        ErrorMessage errorMessage = responseException((HttpStatus) status, fields, WRONG_DATA, request);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {

        Map<String, Object> fields = new HashMap<>();

        exception.getConstraintViolations().forEach((error) -> {
            String path = error.getPropertyPath().toString();
            String fieldName = path.substring(path.lastIndexOf(".") + 1);
            String message = error.getMessage();
            fields.put(fieldName, message);
        });

        ErrorMessage errorMessage = responseException(HttpStatus.BAD_REQUEST, fields, WRONG_DATA, request);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage responseException(HttpStatus status, Map<String, Object> messages, String solution, WebRequest request) {
        return new ErrorMessage(
                status.value(),
                LocalDateTime.now(),
                messages,
                solution,
                request.getDescription(false)
        );
    }
}
