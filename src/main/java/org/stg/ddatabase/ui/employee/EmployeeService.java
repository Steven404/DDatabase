package org.stg.ddatabase.ui.employee;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import okhttp3.*;
import org.stg.ddatabase.api.Routes;
import org.stg.ddatabase.application.DDatabase;

import java.io.IOException;
import java.util.Arrays;

public class EmployeeService {

    private final OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    String responseBody;

    protected Task<String> getEmployees(String token) {
        return new Task<>() {
            @Override
            protected String call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                Request request = new Request.Builder()
                        .url(Routes.GET_ALL_EMPLOYEES.getRoute())
                        .addHeader("Authorization", "Bearer " + token)
                        .get()
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseBody = response.body().string();
                    EmployeeTableModel[] employeeTableModels1 = (EmployeeTableModel[]) gson.fromJson(responseBody, EmployeeTableModel[].class);
                    EmployeeTableModel.employees.addAll(Arrays.asList(employeeTableModels1));
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    e.printStackTrace();
                }
                return "";
            }
        };
    }


}
