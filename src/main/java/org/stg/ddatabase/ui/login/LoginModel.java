package org.stg.ddatabase.ui.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty userToken;

    public LoginModel() {
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.userToken = new SimpleStringProperty();
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

    public String getUserToken() {
        return userToken.get();
    }

    public StringProperty userTokenProperty() {
        return userToken;
    }
}

