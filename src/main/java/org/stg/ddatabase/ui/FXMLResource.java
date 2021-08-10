package org.stg.ddatabase.ui;

public enum FXMLResource {

    LOGIN("/resources/login/login-view.fxml", "resources.login.login-bundle"),
    EMPLOYEE("/resources/employee/employee-view.fxml", "resources.employee.employee-bundle"),
    SIGN_UP("/resources/signup/signup-view.fxml","resources.signup.signup-bundle");

    private final String path;
    private final String bundleName;

    FXMLResource(String path, String bundleName) {
        this.path = path;
        this.bundleName = bundleName;
    }

    public String getPath() {
        return path;
    }

    public String getBundleName() {
        return bundleName;
    }

}