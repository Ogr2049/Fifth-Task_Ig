package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailRequestDto {
    
    @NotBlank(message = "Email получателя не может быть пустым")
    @Email(message = "Неверный формат email")
    private String recipientEmail;
    
    @NotBlank(message = "Тема письма не может быть пустой")
    @Size(min = 2, max = 100, message = "Тема письма должна быть от 2 до 100 символов")
    private String subject;
    
    @NotBlank(message = "Текст письма не может быть пустым")
    @Size(min = 10, max = 1000, message = "Текст письма должен быть от 10 до 1000 символов")
    private String message;
    
    public EmailRequestDto() {}
    
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String myRecipientEmail) { this.recipientEmail = myRecipientEmail; }
    
    public String getSubject() { return subject; }
    public void setSubject(String mySubject) { this.subject = mySubject; }
    
    public String getMessage() { return message; }
    public void setMessage(String myMessage) { this.message = myMessage; }
}