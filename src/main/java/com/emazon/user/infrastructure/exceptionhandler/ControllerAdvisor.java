package com.emazon.user.infrastructure.exceptionhandler;

import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.exceptions.RoleNotFoundException;
import com.emazon.user.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = Constants.RESPONSE_MESSAGE_KEY;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgeNotValidException.class)
    public ResponseEntity<Object> handleAgeNotValidException(AgeNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.AGE_NOT_VALID.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, roleNotFoundException.getMessage()));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_JSON_FORMAT.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, emailAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(DocumentAlreadyExistsException.class)
    public ResponseEntity<Object> handleDocumentAlreadyExistsException(DocumentAlreadyExistsException documentAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, documentAlreadyExistsException.getMessage()));
    }

}
