package com.example.cards.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponseDto {

    private String apiPath;
    private HttpStatus errorCode;
    private List<String> errorMessages;
    private LocalDateTime errorTime;

    public ErrorResponseDto(String apiPath, HttpStatus errorCode, List<String> errorMessages, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
        this.errorTime = errorTime;
    }
}
