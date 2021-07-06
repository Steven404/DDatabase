package org.stg.ddatabase.ui.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {

    private final StringProperty username;
    private final StringProperty password;

    public LoginModel() {
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }
}

