package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    
    @Mock
    private JavaMailSender myJavaMailSender;
    
    @Mock
    private TemplateEngine myTemplateEngine;
    
    @Mock
    private MimeMessage myMimeMessage;
    
    @InjectMocks
    private EmailService myEmailService;
    
    @BeforeEach
    void mySetUp() {
        when(myJavaMailSender.createMimeMessage()).thenReturn(myMimeMessage);
    }
    
    @Test
    void myTestSendSimpleEmailSuccess() throws Exception {
        myEmailService.mySendSimpleEmail("test@example.com", "Test Subject", "Test Message");
        
        verify(myJavaMailSender, times(1)).send(myMimeMessage);
    }
    
    @Test
    void myTestSendSimpleEmailFailure() throws Exception {
        doThrow(new MessagingException("SMTP error")).when(myJavaMailSender).send(any(MimeMessage.class));
        
        assertThrows(com.example.exception.EmailSendingException.class, () -> {
            myEmailService.mySendSimpleEmail("test@example.com", "Test Subject", "Test Message");
        });
    }
    
    @Test
    void myTestSendHtmlEmailSuccess() {
        when(myTemplateEngine.process(anyString(), any(Context.class))).thenReturn("<html>Test</html>");
        
        myEmailService.mySendHtmlEmail("test@example.com", "Test Subject", "test-template", new Context());
        
        verify(myJavaMailSender, times(1)).send(myMimeMessage);
    }
    
    @Test
    void myTestSendWelcomeEmail() {
        when(myTemplateEngine.process(eq("welcome-email"), any(Context.class)))
            .thenReturn("<html>Welcome</html>");
        
        myEmailService.mySendWelcomeEmail("test@example.com", "Иван Иванов");
        
        verify(myJavaMailSender, times(1)).send(myMimeMessage);
    }
}