package com.example.service;

import com.example.dto.UserKafkaEventDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {
    
    @Mock
    private EmailService myEmailService;
    
    @Mock
    private Acknowledgment myAcknowledgment;
    
    @InjectMocks
    private KafkaConsumerService myKafkaConsumerService;
    
    @Test
    void myTestConsumeUserCreatedEvent() {
        UserKafkaEventDto myEventDto = new UserKafkaEventDto();
        myEventDto.setMyEventType("USER_CREATED");
        myEventDto.setMyUserEmail("test@example.com");
        myEventDto.setMyUserName("Иван Иванов");
        myEventDto.setMyEventTimestamp(LocalDateTime.now());
        
        myKafkaConsumerService.myConsumeUserEvent(myEventDto, myAcknowledgment);
        
        verify(myEmailService, times(1)).mySendSimpleEmail(
            eq("test@example.com"),
            eq("Добро пожаловать!"),
            contains("Ваш аккаунт на сайте был успешно создан.")
        );
        
        verify(myAcknowledgment, times(1)).acknowledge();
    }
    
    @Test
    void myTestConsumeUserDeletedEvent() {
        UserKafkaEventDto myEventDto = new UserKafkaEventDto();
        myEventDto.setMyEventType("USER_DELETED");
        myEventDto.setMyUserEmail("test@example.com");
        myEventDto.setMyUserName("Иван Иванов");
        myEventDto.setMyEventTimestamp(LocalDateTime.now());
        
        myKafkaConsumerService.myConsumeUserEvent(myEventDto, myAcknowledgment);
        
        verify(myEmailService, times(1)).mySendSimpleEmail(
            eq("test@example.com"),
            eq("Ваш аккаунт удален"),
            contains("Ваш аккаунт был удалён.")
        );
        
        verify(myAcknowledgment, times(1)).acknowledge();
    }
    
    @Test
    void myTestConsumeUnknownEvent() {
        UserKafkaEventDto myEventDto = new UserKafkaEventDto();
        myEventDto.setMyEventType("UNKNOWN_EVENT");
        myEventDto.setMyUserEmail("test@example.com");
        myEventDto.setMyUserName("Иван Иванов");
        
        myKafkaConsumerService.myConsumeUserEvent(myEventDto, myAcknowledgment);
        
        verify(myEmailService, never()).mySendSimpleEmail(anyString(), anyString(), anyString());
        verify(myAcknowledgment, times(1)).acknowledge();
    }
}