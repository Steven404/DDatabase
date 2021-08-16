package org.stg.ddatabase.ui.signup;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import okhttp3.*;
import org.stg.ddatabase.api.Authentication;
import org.stg.ddatabase.api.Routes;

public class SignupService {
    private final OkHttpClient client = new OkHttpClient();

    int responseCode;

    Gson gson = new Gson();

    protected Task<Integer> signUp(SignupModel signupModel){
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                RequestBody formBody = new FormBody.Builder()
                        .add("first_name", signupModel.getFirstName())
                        .add("last_name", signupModel.getLastName())
                        .add("username", signupModel.getUsername())
                        .add("password", signupModel.getPassword())
                        .build();
                Request request = new Request.Builder()
                        .url(Routes.SIGN_UP.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .post(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()){
                    responseCode = response.code();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                return responseCode;
            }
        };
    }
}
