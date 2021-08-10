package org.stg.ddatabase.ui.signup;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.FXMLResource;

import java.io.IOException;
import java.util.ResourceBundle;

public class SignupController {

    @FXML
    JFXButton returnButton;

    @FXML
    public void initialize(){
        returnButton.setOnAction(event -> {
            try {
                switchToLoginView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
