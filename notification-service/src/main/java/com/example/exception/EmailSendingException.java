package com.example.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String myMessage) {
        super(myMessage);
    }
    
    public EmailSendingException(String myMessage, Throwable myCause) {
        super(myMessage, myCause);
    }
}