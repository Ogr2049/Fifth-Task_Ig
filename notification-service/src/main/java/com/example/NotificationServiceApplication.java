package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(NotificationServiceApplication.class);
    
    public static void main(String[] myArgs) {
        myLoggerInstance.info("Starting Notification Service Application");
        SpringApplication.run(NotificationServiceApplication.class, myArgs);
    }
}