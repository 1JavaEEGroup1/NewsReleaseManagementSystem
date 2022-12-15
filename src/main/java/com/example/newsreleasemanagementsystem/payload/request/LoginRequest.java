package com.example.newsreleasemanagementsystem.payload.request;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@Component
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
