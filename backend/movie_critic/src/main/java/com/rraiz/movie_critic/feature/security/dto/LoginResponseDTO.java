package com.rraiz.movie_critic.feature.security.dto;

import com.rraiz.movie_critic.feature.security.model.ApplicationUser;

public class LoginResponseDTO {

    private String username;
    private String message;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(ApplicationUser user, String message) {
        if (user != null) {
            this.username = user.getUsername();
        }
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
