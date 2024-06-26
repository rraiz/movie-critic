package com.rraiz.movie_critic.feature.security.dto;

public class RegistrationDTO {
    private String username;
    private String password;
    private String email;
    private boolean rememberMe;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, String password, String email, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
