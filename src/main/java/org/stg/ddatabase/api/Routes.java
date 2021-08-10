package org.stg.ddatabase.api;

public enum Routes {
    LOG_IN("http://localhost:3000/api/users/login"),
    GET_ALL_EMPLOYEES("http://localhost:3000/api/employees/"),
    INSERT_EMPLOYEE("http://localhost:3000/api/employees/createEmployee"),
    UPDATE_EMPLOYEE_REST_DAYS("http://localhost:3000/api/employees/updateEmployeeRestDays"),
    DELETE_EMPLOYEE("http://localhost:3000/api/employees/deleteEmployee"),
    GET_EMPLOYEE_BY_LASTNAME("http://localhost:3000/api/employees/getEmployeeByLastName");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
