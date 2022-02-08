package org.stg.ddatabase.ui.signup;

import animatefx.animation.SlideOutDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.FXMLResource;
import org.stg.ddatabase.ui.dialog.DLG;

import java.io.IOException;
import java.util.ResourceBundle;

public class SignupController {
    SignupModel signupModel = new SignupModel();
    SignupService signupService = new SignupService();

    @FXML
    ScrollPane scrollPane;

    @FXML
    JFXButton signupButton,returnButton;

    @FXML
    JFXTextField firstnameTextField, lastnameTextField, usernameTextField;

    @FXML
    JFXPasswordField passwordTextField,confirmPasswordTextField;

    String firstname,lastname,username,password,confirmPassword;

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
        firstname = signupModel.getFirstName();
        lastname = signupModel.getLastName();
        username = signupModel.getLastName();
        password = signupModel.getPassword();
        confirmPassword = signupModel.getPassword();
        if (firstname == null || firstname.trim().isEmpty() || lastname == null || lastname.trim().isEmpty() || username == null || username.trim().isEmpty() ||
        password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()){
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Παρακαλώ συμπληρώστε όλα τα πεδία.");
            DLG.WARNING.show();
            return;

        }
        if( !confirmPasswordTextField.getText().equals(passwordTextField.getText())){
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Τα πεδία Password και Confirm Password δεν είναι ίδια.");
            DLG.WARNING.show();
            return;
        }
        Task<Integer> signUpTask = signupService.signUp(signupModel);
        signUpTask.setOnRunning(workerStateEvent -> DDatabase.getScene().setCursor(Cursor.WAIT));
        signUpTask.setOnSucceeded(workerStateEvent -> {
            switch (signUpTask.getValue()){
                case 0:
                    DLG.ERROR.setHeader("Σφάλμα");
                    DLG.ERROR.setContentText("Σφάλμα σύνδεσης με τον διακομιστή. Επικοινωνήστε με τον διαχειριστή του συστήματος.");
                    DLG.ERROR.show();
                    break;
                case 501:
                    DLG.ERROR.setHeader("Σφάλμα");
                    DLG.ERROR.setContentText("Υπάρχει ήδη χρήστης στο σύστημα με αυτό το username.");
                    DLG.ERROR.show();
                    break;
                case 403:
                    DLG.ERROR.setHeader("Σφάλμα");
                    DLG.ERROR.setContentText("Δεν μπορείτε να χρησιμοποιήσεται Username διαχειριστή!");
                    DLG.ERROR.show();
                    break;
                case 200:
                    DLG.INFORMATION.setHeader("Πληροφορία");
                    DLG.INFORMATION.setContentText("Επιτυχής δημιουργία χρήστη!");
                    DLG.INFORMATION.show();
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
