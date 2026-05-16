package com.example.service;

import com.example.dto.UserKafkaEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    @Autowired
    private EmailService myEmailService;
    
    @KafkaListener(
        topics = "${spring.kafka.topic.user-events}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "myKafkaListenerContainerFactory"
    )
    public void myConsumeUserEvent(@Payload UserKafkaEventDto myUserEventDto, 
                                   Acknowledgment myAcknowledgment) {
        
        myLoggerInstance.info("Получено событие из Kafka: {}", myUserEventDto);
        
        try {
            String myUserEmail = myUserEventDto.getMyUserEmail();
            String myUserName = myUserEventDto.getMyUserName();
            
            switch (myUserEventDto.getMyEventType()) {
                case "USER_CREATED":
                    myLoggerInstance.info("Отправка приветственного email для пользователя: {}", myUserEmail);
                    myEmailService.mySendSimpleEmail(
                        myUserEmail,
                        "Добро пожаловать!",
                        "Здравствуйте, " + myUserName + "! Ваш аккаунт на сайте был успешно создан."
                    );
                    break;
                    
                case "USER_DELETED":
                    myLoggerInstance.info("Отправка прощального email для пользователя: {}", myUserEmail);
                    myEmailService.mySendSimpleEmail(
                        myUserEmail,
                        "Ваш аккаунт удален",
                        "Здравствуйте, " + myUserName + "! Ваш аккаунт был удалён."
                    );
                    break;
                    
                default:
                    myLoggerInstance.warn("Неизвестный тип события: {}", myUserEventDto.getMyEventType());
                    break;
            }
            
            myAcknowledgment.acknowledge();
            myLoggerInstance.info("Событие успешно обработано и подтверждено");
            
        } catch (Exception myException) {
            myLoggerInstance.error("Ошибка при обработке события: {}", myException.getMessage());
        }
    }
}