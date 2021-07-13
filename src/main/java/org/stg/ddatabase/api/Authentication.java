package org.stg.ddatabase.api;

public class Authentication {

    public static String token;

    public static void setToken(String s){
        token = s;
    }

    public static String getToken(){
        return token;
    }
}
