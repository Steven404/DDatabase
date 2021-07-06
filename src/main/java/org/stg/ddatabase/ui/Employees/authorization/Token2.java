package org.stg.ddatabase.ui.Employees.authorization;

public class Token2 {

    private static volatile
        Token2 token2instance;

    private static String tkn;

    public Token2(String tkn) {
        this.tkn = tkn;
    }

    public static Token2 instance(String tkn){
        Token2 result = token2instance;
        if (result == null){
            synchronized (Token2.class){
                result = token2instance;
                if (result == null){
                    token2instance = result = new Token2(tkn);
                }
            }
        }
        return result;
    }
}
