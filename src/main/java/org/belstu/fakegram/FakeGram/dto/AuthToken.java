package org.belstu.fakegram.FakeGram.dto;

import javax.validation.constraints.NotNull;

public class AuthToken {
    @NotNull(message = "Token can't be null")
    private String token;

    public AuthToken() {
    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
