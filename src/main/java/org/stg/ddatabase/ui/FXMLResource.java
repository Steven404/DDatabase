package org.stg.ddatabase.ui;

public enum FXMLResource {

    LOGIN("/resources/login/login-view.fxml", "resources.login.login-bundle"),
    EMPLOYEES("/resources/employees/employees-view.fxml", "resources.employees.employees-bundle");

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