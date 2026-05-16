package com.example.service;

import com.example.dto.UserKafkaEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(KafkaProducerService.class);
    
    @Autowired
    private KafkaTemplate<String, Object> myKafkaTemplate;
    
    @Value("${spring.kafka.topic.user-events}")
    private String myUserEventsTopic;
    
    public void mySendUserEvent(UserKafkaEventDto myUserEventDto) {
        myLoggerInstance.info("Отправка Kafka события: {} в топик: {}", myUserEventDto, myUserEventsTopic);
        
        try {
            CompletableFuture<SendResult<String, Object>> myFuture = 
                myKafkaTemplate.send(myUserEventsTopic, myUserEventDto.getMyUserEmail(), myUserEventDto);
            
            myFuture.whenComplete((myResult, myException) -> {
                if (myException == null) {
                    myLoggerInstance.info("Событие успешно отправлено в Kafka. Offset: {}", 
                        myResult.getRecordMetadata().offset());
                } else {
                    myLoggerInstance.error("Ошибка при отправке события в Kafka: {}", myException.getMessage());
                }
            });
        } catch (Exception myException) {
            myLoggerInstance.error("Исключение при отправке события в Kafka: {}", myException.getMessage());
        }
    }
}