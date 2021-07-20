package org.stg.ddatabase.api;

public enum Routes {
    LOGIN("http://localhost:3000/api/users/login"),
    GETALLEMPLOYEES("http://localhost:3000/api/employees/getEmployees");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
