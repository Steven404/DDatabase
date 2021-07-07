package org.stg.ddatabase.ui.Employees;

import javafx.fxml.FXML;

public class EmployeesController {
    public static Token token;

    protected static String userToken;

    @FXML
    public void initialize(){
        userToken = token.getToken();
        token.setToken(null);
        System.out.println("Toke class: "+ token.getToken());
        if (!(userToken == null)){
            System.out.println(userToken);
        } else {
            System.out.println("User token is null.");
        }
    }
}
