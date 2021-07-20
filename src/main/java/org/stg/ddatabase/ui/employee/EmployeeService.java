package org.stg.ddatabase.ui.employee;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import okhttp3.*;
import org.stg.ddatabase.api.Authentication;
import org.stg.ddatabase.api.Routes;
import org.stg.ddatabase.application.DDatabase;

import java.io.IOException;

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
                        .url(Routes.GETALLEMPLOYEES.getRoute())
                        .addHeader("Authorization", "Bearer " + token)
                        .get()
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseBody = response.body().string();
                    EmployeeModel[] employeeModels1 = (EmployeeModel[]) gson.fromJson(responseBody, EmployeeModel[].class);
                    for (int i = 0; i < employeeModels1.length; i++) {
                        EmployeeModel.employees.add(employeeModels1[i]);
                    }
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
