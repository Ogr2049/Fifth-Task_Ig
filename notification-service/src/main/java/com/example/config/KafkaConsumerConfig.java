package com.example.config;

import com.example.dto.UserKafkaEventDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String myBootstrapServers;
    
    @Value("${spring.kafka.consumer.group-id}")
    private String myGroupId;
    
    @Bean
    public ConsumerFactory<String, UserKafkaEventDto> myConsumerFactory() {
        Map<String, Object> myConfigProps = new HashMap<>();
        myConfigProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, myBootstrapServers);
        myConfigProps.put(ConsumerConfig.GROUP_ID_CONFIG, myGroupId);
        myConfigProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        myConfigProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        myConfigProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        myConfigProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.dto");
        myConfigProps.put(JsonDeserializer.TYPE_MAPPINGS, "userEvent:com.example.dto.UserKafkaEventDto");
        
        return new DefaultKafkaConsumerFactory<>(
            myConfigProps,
            new StringDeserializer(),
            new JsonDeserializer<>(UserKafkaEventDto.class)
        );
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserKafkaEventDto> 
            myKafkaListenerContainerFactory() {
        
        ConcurrentKafkaListenerContainerFactory<String, UserKafkaEventDto> myFactory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        myFactory.setConsumerFactory(myConsumerFactory());
        myFactory.setConcurrency(3);
        
        return myFactory;
    }
}