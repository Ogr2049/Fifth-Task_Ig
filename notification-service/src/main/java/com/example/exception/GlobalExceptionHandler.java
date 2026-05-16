package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Map<String, Object>> myHandleEmailSendingException(EmailSendingException myException) {
        myLoggerInstance.error("Ошибка отправки email: {}", myException.getMessage());
        
        Map<String, Object> myErrorResponse = new HashMap<>();
        myErrorResponse.put("timestamp", LocalDateTime.now());
        myErrorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        myErrorResponse.put("error", "Email Sending Failed");
        myErrorResponse.put("message", myException.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myErrorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> myHandleValidationException(
            MethodArgumentNotValidException myException) {
        
        Map<String, String> myFieldErrors = new HashMap<>();
        for (FieldError myFieldError : myException.getBindingResult().getFieldErrors()) {
            myFieldErrors.put(myFieldError.getField(), myFieldError.getDefaultMessage());
        }
        
        Map<String, Object> myErrorResponse = new HashMap<>();
        myErrorResponse.put("timestamp", LocalDateTime.now());
        myErrorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        myErrorResponse.put("error", "Validation Failed");
        myErrorResponse.put("message", "Ошибка валидации запроса");
        myErrorResponse.put("errors", myFieldErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myErrorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> myHandleGeneralException(Exception myException) {
        myLoggerInstance.error("Внутренняя ошибка сервера: {}", myException.getMessage(), myException);
        
        Map<String, Object> myErrorResponse = new HashMap<>();
        myErrorResponse.put("timestamp", LocalDateTime.now());
        myErrorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        myErrorResponse.put("error", "Internal Server Error");
        myErrorResponse.put("message", "Произошла внутренняя ошибка сервера");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myErrorResponse);
    }
}