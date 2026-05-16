package com.example.service;

import com.example.dto.EmailRequestDto;
import com.example.dto.EmailResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private EmailService myEmailService;
    
    public EmailResponseDto mySendEmailNotification(EmailRequestDto myEmailRequestDto) {
        myLoggerInstance.info("Отправка email уведомления на адрес: {}", myEmailRequestDto.getRecipientEmail());
        
        try {
            myEmailService.mySendSimpleEmail(
                myEmailRequestDto.getRecipientEmail(),
                myEmailRequestDto.getSubject(),
                myEmailRequestDto.getMessage()
            );
            
            return new EmailResponseDto(
                true,
                "Email успешно отправлен",
                myEmailRequestDto.getRecipientEmail()
            );
            
        } catch (Exception myException) {
            myLoggerInstance.error("Ошибка отправки email: {}", myException.getMessage());
            return new EmailResponseDto(
                false,
                "Ошибка отправки email: " + myException.getMessage(),
                myEmailRequestDto.getRecipientEmail()
            );
        }
    }
}