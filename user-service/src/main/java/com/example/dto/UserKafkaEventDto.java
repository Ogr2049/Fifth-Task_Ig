package com.example.dto;

import java.time.LocalDateTime;

public class UserKafkaEventDto {
    private String myEventType;
    private String myUserEmail;
    private String myUserName;
    private LocalDateTime myEventTimestamp;
    
    public UserKafkaEventDto() {}
    
    public UserKafkaEventDto(String myEventType, String myUserEmail, String myUserName) {
        this.myEventType = myEventType;
        this.myUserEmail = myUserEmail;
        this.myUserName = myUserName;
        this.myEventTimestamp = LocalDateTime.now();
    }
    
    public String getMyEventType() { return myEventType; }
    public void setMyEventType(String myEventType) { this.myEventType = myEventType; }
    
    public String getMyUserEmail() { return myUserEmail; }
    public void setMyUserEmail(String myUserEmail) { this.myUserEmail = myUserEmail; }
    
    public String getMyUserName() { return myUserName; }
    public void setMyUserName(String myUserName) { this.myUserName = myUserName; }
    
    public LocalDateTime getMyEventTimestamp() { return myEventTimestamp; }
    public void setMyEventTimestamp(LocalDateTime myEventTimestamp) { this.myEventTimestamp = myEventTimestamp; }
    
    @Override
    public String toString() {
        return String.format("UserKafkaEventDto[eventType='%s', userEmail='%s', userName='%s', eventTimestamp=%s]", 
                           myEventType, myUserEmail, myUserName, myEventTimestamp);
    }
}