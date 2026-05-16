package com.example.controller;

import com.example.dto.EmailRequestDto;
import com.example.dto.EmailResponseDto;
import com.example.service.NotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(NotificationController.class);
    
    @Autowired
    private NotificationService myNotificationService;
    
    @PostMapping("/send-email")
    public ResponseEntity<EmailResponseDto> mySendEmail(
            @Valid @RequestBody EmailRequestDto myEmailRequestDto) {
        
        myLoggerInstance.info("REST запрос на отправку email на адрес: {}", 
            myEmailRequestDto.getRecipientEmail());
        
        EmailResponseDto myResponse = myNotificationService.mySendEmailNotification(myEmailRequestDto);
        
        if (myResponse.isSuccess()) {
            return ResponseEntity.ok(myResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myResponse);
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> myHealthCheck() {
        return ResponseEntity.ok("Notification Service is UP");
    }
}