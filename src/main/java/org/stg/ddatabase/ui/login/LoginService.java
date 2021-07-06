package org.stg.ddatabase.ui.login;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import okhttp3.*;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.Employees.EmployeesController;
import org.stg.ddatabase.ui.Employees.Token;

import java.io.IOException;

public class LoginService {

    private final OkHttpClient client = new OkHttpClient();

    int responseCode;

    Gson gson = new Gson();

    protected Task<Integer> login(LoginModel loginModel) throws IOException {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                RequestBody formBody = new FormBody.Builder()
                        .add("username", loginModel.getUsername())
                        .add("password", loginModel.getPassword())
                        .build();
                Request request = new Request.Builder()
                        .url("http://localhost:3000/api/users/login")
                        .post(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    System.out.println(response.code());
                    responseCode = response.code();
                    String responseToken= response.body().string();
                    EmployeesController.userToken = gson.fromJson(responseToken, Token.class);
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                }
                return responseCode;
            }
        };
    }
}
