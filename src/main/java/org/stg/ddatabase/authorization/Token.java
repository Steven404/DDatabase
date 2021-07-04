package org.stg.ddatabase.authorization;

import javafx.beans.property.StringProperty;

public class Token {
    private final StringProperty userToken;

    public Token(StringProperty userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken.get();
    }

    public StringProperty userTokenProperty() {
        return userToken;
    }
}
