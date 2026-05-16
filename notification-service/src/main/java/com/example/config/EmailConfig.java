package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    
    @Bean
    public JavaMailSender myJavaMailSender(org.springframework.boot.autoconfigure.mail.MailProperties myMailProperties) {
        JavaMailSenderImpl myMailSender = new JavaMailSenderImpl();
        myMailSender.setHost(myMailProperties.getHost());
        myMailSender.setPort(myMailProperties.getPort());
        myMailSender.setUsername(myMailProperties.getUsername());
        myMailSender.setPassword(myMailProperties.getPassword());
        
        Properties myProps = myMailSender.getJavaMailProperties();
        myProps.put("mail.transport.protocol", "smtp");
        myProps.put("mail.smtp.auth", "true");
        myProps.put("mail.smtp.starttls.enable", "true");
        myProps.put("mail.debug", "false");
        
        return myMailSender;
    }
}