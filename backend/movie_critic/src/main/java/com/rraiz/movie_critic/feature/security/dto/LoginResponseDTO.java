package com.rraiz.movie_critic.feature.security.dto;

import com.rraiz.movie_critic.feature.security.model.ApplicationUser;

public class LoginResponseDTO {

    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(ApplicationUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
