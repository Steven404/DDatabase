package org.stg.ddatabase.ui.Employees;

public class EmployeesController {
    public static Token userToken;

    public void initialize(){
        if (!(userToken == null)){
            System.out.println(userToken.getToken());
        } else {
            System.out.println("User token is null.");
        }
    }
}
