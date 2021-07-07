package org.stg.ddatabase.ui.Employees;

public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    protected String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}