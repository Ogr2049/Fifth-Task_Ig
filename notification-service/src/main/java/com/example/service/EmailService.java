package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender myJavaMailSender;
    
    @Autowired
    private TemplateEngine myTemplateEngine;
    
    @Async
    public void mySendSimpleEmail(String myTo, String mySubject, String myText) {
        try {
            MimeMessage myMessage = myJavaMailSender.createMimeMessage();
            MimeMessageHelper myHelper = new MimeMessageHelper(myMessage, true, "UTF-8");
            
            myHelper.setTo(myTo);
            myHelper.setSubject(mySubject);
            myHelper.setText(myText, false);
            
            myJavaMailSender.send(myMessage);
            myLoggerInstance.info("Email успешно отправлен на адрес: {}", myTo);
        } catch (MessagingException myException) {
            myLoggerInstance.error("Ошибка при отправке email на адрес {}: {}", myTo, myException.getMessage());
            throw new com.example.exception.EmailSendingException(
                "Не удалось отправить email на адрес: " + myTo, myException);
        }
    }
    
    @Async
    public void mySendHtmlEmail(String myTo, String mySubject, String myTemplateName, 
                                Context myContext) {
        try {
            MimeMessage myMessage = myJavaMailSender.createMimeMessage();
            MimeMessageHelper myHelper = new MimeMessageHelper(myMessage, true, "UTF-8");
            
            String myHtmlContent = myTemplateEngine.process(myTemplateName, myContext);
            
            myHelper.setTo(myTo);
            myHelper.setSubject(mySubject);
            myHelper.setText(myHtmlContent, true);
            
            myJavaMailSender.send(myMessage);
            myLoggerInstance.info("HTML email успешно отправлен на адрес: {}", myTo);
        } catch (MessagingException myException) {
            myLoggerInstance.error("Ошибка при отправке HTML email на адрес {}: {}", myTo, myException.getMessage());
            throw new com.example.exception.EmailSendingException(
                "Не удалось отправить HTML email на адрес: " + myTo, myException);
        }
    }
    
    public void mySendWelcomeEmail(String myTo, String myUserName) {
        Context myContext = new Context();
        myContext.setVariable("userName", myUserName);
        myContext.setVariable("welcomeMessage", "Добро пожаловать на наш сайт!");
        
        mySendHtmlEmail(myTo, "Добро пожаловать!", "welcome-email", myContext);
    }
    
    public void mySendGoodbyeEmail(String myTo, String myUserName) {
        Context myContext = new Context();
        myContext.setVariable("userName", myUserName);
        myContext.setVariable("goodbyeMessage", "Спасибо, что были с нами!");
        
        mySendHtmlEmail(myTo, "Ваш аккаунт удален", "goodbye-email", myContext);
    }
}