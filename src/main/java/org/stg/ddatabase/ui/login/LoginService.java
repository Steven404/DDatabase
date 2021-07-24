package org.stg.ddatabase.ui.login;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import okhttp3.*;
import org.json.JSONObject;
import org.stg.ddatabase.api.Authentication;
import org.stg.ddatabase.api.Routes;
import org.stg.ddatabase.application.DDatabase;

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
                        .url(Routes.LOG_IN.getRoute())
                        .post(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseCode = response.code();
                    String responseToken= response.body().string();
                    JSONObject jsonObject = new JSONObject(responseToken);
                    Authentication.setToken(jsonObject.getString("token"));
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
