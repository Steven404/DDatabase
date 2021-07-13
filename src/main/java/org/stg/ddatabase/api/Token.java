package org.stg.ddatabase.api;

import org.stg.ddatabase.api.Authentication;

public enum Token {
    JWT(Authentication.getToken());

    private String token;

    Token(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
