package org.stg.ddatabase.ui.employee;

import javafx.concurrent.Task;
import javafx.scene.Cursor;
import org.stg.ddatabase.application.DDatabase;

import java.io.IOException;

public class EmployeeService {

    protected Task<String> getEmployees(String token) throws IOException {
        return new Task<>() {
            @Override
            protected String call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);

                return "";
            }
        };
    }


}
