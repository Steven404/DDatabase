package org.stg.ddatabase.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.FXMLResource;
import org.stg.ddatabase.ui.dialog.Dialog;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {
    private LoginModel loginModel = new LoginModel();
    private LoginService loginService = new LoginService();

    @FXML
    HBox hBox;

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox vbox = new VBox();

    @FXML
    AnchorPane anchorPane;

    @FXML
    JFXTextField usernameTxtField;

    @FXML
    JFXPasswordField passwordTxtField;

    @FXML
    JFXButton loginButton = new JFXButton();

    @FXML
    JFXButton signupButton = new JFXButton();

    @FXML
    public void initialize() {
        usernameTxtField.textProperty().bindBidirectional(loginModel.usernameProperty());
        passwordTxtField.textProperty().bindBidirectional(loginModel.passwordProperty());
        loginButton.setOnAction(event -> loginButtonAction());
    }

    private void loginButtonAction() {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            Dialog dialog = new Dialog(Alert.AlertType.WARNING, "Warning", "Username/Password fields cannot be empty!");
            dialog.show();
            return;
        }
        try {
            signupButton.setDisable(true);
            loginButton.setDisable(true);
            passwordTxtField.setDisable(true);
            usernameTxtField.setDisable(true);
            Task<Integer> loginTask = loginService.login(loginModel);
            loginTask.setOnSucceeded(workerStateEvent -> {
                Dialog dialog;
                switch (loginTask.getValue()) {
                    case 0:
                        dialog = new Dialog(Alert.AlertType.ERROR, "Error", "Failed to connect to API.");
                        dialog.show();
                        signupButton.setDisable(false);
                        loginButton.setDisable(false);
                        passwordTxtField.setDisable(false);
                        usernameTxtField.setDisable(false);
                        break;
                    case 200:
                        dialog = new Dialog(Alert.AlertType.INFORMATION, "Success", "Successful log in!");
                        dialog.showAndWait();
                        signupButton.setDisable(false);
                        loginButton.setDisable(false);
                        passwordTxtField.setDisable(false);
                        usernameTxtField.setDisable(false);
                        sceneFadeTransition();
                        break;
                    case 404:
                        dialog = new Dialog(Alert.AlertType.WARNING, "Warning", "User is not signed up.");
                        dialog.show();
                        signupButton.setDisable(false);
                        loginButton.setDisable(false);
                        passwordTxtField.setDisable(false);
                        usernameTxtField.setDisable(false);
                        break;
                    case 400:
                        dialog = new Dialog(Alert.AlertType.WARNING, "Warning", "Invalid Username/Password combination.");
                        dialog.show();
                        signupButton.setDisable(false);
                        loginButton.setDisable(false);
                        passwordTxtField.setDisable(false);
                        usernameTxtField.setDisable(false);
                        break;
                    case 405:
                        dialog = new Dialog(Alert.AlertType.ERROR, "Error", "Failed connecting to the database. Please check if MySQL is running!");
                        dialog.show();
                        signupButton.setDisable(false);
                        loginButton.setDisable(false);
                        passwordTxtField.setDisable(false);
                        usernameTxtField.setDisable(false);
                        break;
                }
            });
            loginTask.setOnFailed(workerStateEvent -> {
                Dialog dialog = new Dialog(Alert.AlertType.ERROR, "Error", "Failed to connect to API");
                dialog.show();
                signupButton.setDisable(false);
                loginButton.setDisable(false);
                passwordTxtField.setDisable(false);
                usernameTxtField.setDisable(false);
            });
            Thread thread = new Thread(loginTask);
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sceneFadeTransition(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250));
        fadeTransition.setNode(hBox);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            hBox.opacityProperty().set(0);
            try {
                switchToEmployeeView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void switchToEmployeeView() throws IOException {
        Scene scene = new Scene(loadFXML(FXMLResource.EMPLOYEES));
        DDatabase.getMainStage().setScene(scene);
        DDatabase.getMainStage().show();
    }

    private static Parent loadFXML(FXMLResource fxmlResource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DDatabase.class.getResource(fxmlResource.getPath()), ResourceBundle.getBundle(fxmlResource.getBundleName()));
        return fxmlLoader.load();
    }

}