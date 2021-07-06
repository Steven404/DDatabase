package org.stg.ddatabase.ui.Employees;

public class Token {

    private static String token;

    public Token(String token) {
        this.token = token;
    }

    protected static String getToken(){
        return token;
    }
}