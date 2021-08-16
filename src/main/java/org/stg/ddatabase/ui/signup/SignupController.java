package org.stg.ddatabase.ui.signup;

import animatefx.animation.SlideOutDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.FXMLResource;
import org.stg.ddatabase.ui.dialog.DLG;

import java.io.IOException;
import java.util.ResourceBundle;

public class SignupController {
    SignupModel signupModel = new SignupModel();
    SignupService signupService = new SignupService();

    @FXML
    JFXButton signupButton,returnButton;

    @FXML
    JFXTextField firstnameTextField, lastnameTextField, usernameTextField;

    @FXML
    JFXPasswordField passwordTextField;

    @FXML
    public void initialize(){
        firstnameTextField.textProperty().bindBidirectional(signupModel.firstNameProperty());
        lastnameTextField.textProperty().bindBidirectional(signupModel.lastNameProperty());
        usernameTextField.textProperty().bindBidirectional(signupModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(signupModel.passwordProperty());
        signupButton.setOnAction(event -> signUp());
        returnButton.setOnAction(event -> {
            try {
                switchToLoginView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void signUp(){
        Task<Integer> signUpTask = signupService.signUp(signupModel);
        signUpTask.setOnSucceeded(workerStateEvent -> {
            switch (signUpTask.getValue()){
                case 400:
                    DLG.ERROR.setHeader("Σφάλμα");
                    DLG.ERROR.setContentText("Υπάρχει ήδη χρήστης στο σύστημα με αυτό το username.");
                    DLG.ERROR.show();
                    break;
                case 201:
                    DLG.INFORMATION.setHeader("Πληροφορία");
                    DLG.INFORMATION.setContentText("Επιτυχής δημιουργία χρήστη!");
                    break;
            }
        });
        signUpTask.setOnFailed(workerStateEvent -> {
            signUpTask.getException().printStackTrace();
        });
        Thread thread = new Thread(signUpTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void switchToLoginView() throws IOException {
        Scene scene = new Scene(loadFXML(FXMLResource.LOGIN));
        DDatabase.getMainStage().setScene(scene);
        DDatabase.getMainStage().show();
        scene.getWindow().setWidth(400);
        scene.getWindow().setHeight(600);
        DDatabase.getMainStage().centerOnScreen();
    }


    private static Parent loadFXML(FXMLResource fxmlResource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DDatabase.class.getResource(fxmlResource.getPath()), ResourceBundle.getBundle(fxmlResource.getBundleName()));
        return fxmlLoader.load();
    }
}
