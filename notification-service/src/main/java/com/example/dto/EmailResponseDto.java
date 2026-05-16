package com.example.dto;

import java.time.LocalDateTime;

public class EmailResponseDto {
    private boolean success;
    private String message;
    private String recipientEmail;
    private LocalDateTime sentAt;
    
    public EmailResponseDto() {}
    
    public EmailResponseDto(boolean mySuccess, String myMessage, String myRecipientEmail) {
        this.success = mySuccess;
        this.message = myMessage;
        this.recipientEmail = myRecipientEmail;
        this.sentAt = LocalDateTime.now();
    }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean mySuccess) { this.success = mySuccess; }
    
    public String getMessage() { return message; }
    public void setMessage(String myMessage) { this.message = myMessage; }
    
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String myRecipientEmail) { this.recipientEmail = myRecipientEmail; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime mySentAt) { this.sentAt = mySentAt; }
}