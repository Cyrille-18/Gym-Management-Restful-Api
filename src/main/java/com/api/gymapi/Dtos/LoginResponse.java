package com.api.gymapi.Dtos;

import com.api.gymapi.models.User;
import lombok.Data;

@Data
public class LoginResponse {
    private User user;
    private String token;

    // Constructeur

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
