package com.example.controller;

import com.example.dto.EmailRequestDto;
import com.example.dto.EmailResponseDto;
import com.example.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {
    
    @Autowired
    private MockMvc myMockMvc;
    
    @Autowired
    private ObjectMapper myObjectMapper;
    
    @MockBean
    private NotificationService myNotificationService;
    
    private EmailRequestDto myValidEmailRequest;
    private EmailResponseDto mySuccessResponse;
    private EmailResponseDto myFailureResponse;
    
    @BeforeEach
    void mySetUp() {
        myValidEmailRequest = new EmailRequestDto();
        myValidEmailRequest.setRecipientEmail("test@example.com");
        myValidEmailRequest.setSubject("Test Subject");
        myValidEmailRequest.setMessage("Test Message");
        
        mySuccessResponse = new EmailResponseDto(true, "Email успешно отправлен", "test@example.com");
        myFailureResponse = new EmailResponseDto(false, "Ошибка отправки", "test@example.com");
    }
    
    @Test
    void myTestSendEmailSuccess() throws Exception {
        when(myNotificationService.mySendEmailNotification(any(EmailRequestDto.class)))
            .thenReturn(mySuccessResponse);
        
        myMockMvc.perform(post("/api/v1/notifications/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(myObjectMapper.writeValueAsString(myValidEmailRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.recipientEmail").value("test@example.com"));
    }
    
    @Test
    void myTestSendEmailFailure() throws Exception {
        when(myNotificationService.mySendEmailNotification(any(EmailRequestDto.class)))
            .thenReturn(myFailureResponse);
        
        myMockMvc.perform(post("/api/v1/notifications/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(myObjectMapper.writeValueAsString(myValidEmailRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false));
    }
    
    @Test
    void myTestSendEmailValidationFailure() throws Exception {
        EmailRequestDto myInvalidRequest = new EmailRequestDto();
        myInvalidRequest.setRecipientEmail("invalid-email");
        myInvalidRequest.setSubject("");
        myInvalidRequest.setMessage("");
        
        myMockMvc.perform(post("/api/v1/notifications/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(myObjectMapper.writeValueAsString(myInvalidRequest)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void myTestHealthCheck() throws Exception {
        myMockMvc.perform(get("/api/v1/notifications/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification Service is UP"));
    }
}