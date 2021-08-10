package org.stg.ddatabase.ui.employee;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import okhttp3.*;
import org.stg.ddatabase.api.Authentication;
import org.stg.ddatabase.api.Routes;
import org.stg.ddatabase.application.DDatabase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class EmployeeService {

    private final OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    String responseBody;

    int responseCode;

    protected Task<Integer> getEmployees() {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                Request request = new Request.Builder()
                        .url(Routes.GET_ALL_EMPLOYEES.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .get()
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseBody = response.body().string();
                    responseCode = response.code();
                    System.out.println(responseCode);
                    EmployeeTableModel[] employeeTableModels1 = gson.fromJson(responseBody, EmployeeTableModel[].class);
                    EmployeeTableModel.employees.addAll(Arrays.asList(employeeTableModels1));
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    e.printStackTrace();
                }
                return responseCode;
            }
        };
    }

    protected Task<Integer> insertEmployee(EmployeeModel employeeModel) {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                RequestBody formBody = new FormBody.Builder()
                        .add("FirstName", employeeModel.getFirstName())
                        .add("LastName", employeeModel.getLastName())
                        .add("FatherName", employeeModel.getFatherName())
                        .add("RecruitmentDate", String.valueOf(employeeModel.getRecruitmentDate()))
                        .add("UntilDate", String.valueOf(employeeModel.getUntilDate()))
                        .add("AFM", employeeModel.getAFM())
                        .add("AMKA", employeeModel.getAMKA())
                        .add("PhoneNo", employeeModel.getPhoneNo())
                        .add("email", employeeModel.getEmail())
                        .add("IBAN", employeeModel.getIBAN())
                        .build();
                Request request = new Request.Builder()
                        .url(Routes.INSERT_EMPLOYEE.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .post(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseCode = response.code();
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    System.out.println(e.getMessage());
                }
                return responseCode;
            }
        };
    }

    protected Task<Integer> updateEmployeeRestDays(EmployeeModel employeeModel, int newRestDays) {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                RequestBody formBody = new FormBody.Builder()
                        .add("ID", String.valueOf(employeeModel.getID()))
                        .add("newRestDays", String.valueOf(newRestDays))
                        .build();
                Request request = new Request.Builder()
                        .url(Routes.UPDATE_EMPLOYEE_REST_DAYS.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .patch(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    responseBody = Objects.requireNonNull(response.body()).string();
                    responseCode = response.code();
                    System.out.println(responseCode);
                    EmployeeTableModel[] employeeTableModels1 = gson.fromJson(responseBody, EmployeeTableModel[].class);
                    EmployeeTableModel.employees.addAll(Arrays.asList(employeeTableModels1));
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    e.printStackTrace();
                }
                return responseCode;
            }
        };
    }

    protected Task<Integer> deleteEmployee(EmployeeModel employeeModel) {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                FormBody formBody = new FormBody.Builder()
                        .add("ID", String.valueOf(employeeModel.getID()))
                        .build();
                Request request = new Request.Builder()
                        .url(Routes.DELETE_EMPLOYEE.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .delete(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    System.out.println(response.body().string());
                    System.out.println("wtf inside task");
                    responseCode = response.code();
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    e.printStackTrace();
                }
                return responseCode;
            }
        };
    }
    protected Task<Integer> getEmployee(EmployeeModel employeeModel) {
        return new Task<>() {
            @Override
            protected Integer call() {
                DDatabase.getScene().setCursor(Cursor.WAIT);
                FormBody formBody = new FormBody.Builder()
                        .add("ID", String.valueOf(employeeModel.getID()))
                        .build();
                Request request = new Request.Builder()
                        .url(Routes.GET_EMPLOYEE_BY_LASTNAME.getRoute())
                        .addHeader("Authorization", "Bearer " + Authentication.getToken())
                        .delete(formBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    System.out.println(response.body().string());
                    System.out.println("wtf inside task");
                    responseCode = response.code();
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                } catch (IOException e) {
                    DDatabase.getScene().setCursor(Cursor.DEFAULT);
                    e.printStackTrace();
                }
                return responseCode;
            }
        };
    }

}