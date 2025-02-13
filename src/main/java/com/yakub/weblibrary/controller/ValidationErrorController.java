package com.yakub.weblibrary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationErrorController {

    static class ValidationErrorResponse {
        private List<ValidationError> errors;

        public ValidationErrorResponse(List<ValidationError> errors) {
            this.errors = errors;
        }

        public List<ValidationError> getErrors() {
            return errors;
        }

        public void setErrors(List<ValidationError> errors) {
            this.errors = errors;
        }
    }

    static class ValidationError {
        private String field;
        private String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ValidationErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
