package com.borges.Scheduler.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(){ return ResponseEntity.notFound().build(); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException ex){
        var error = ex.getFieldErrors();
        return  ResponseEntity.badRequest().body(error.stream().map(ErrorValidationData::new).toList());
    }

    private record ErrorValidationData(String field, String message) {

        public ErrorValidationData(FieldError error) { this(error.getField(), error.getDefaultMessage());}
    }
}
