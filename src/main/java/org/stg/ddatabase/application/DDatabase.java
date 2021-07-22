package org.stg.ddatabase.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.stg.ddatabase.ui.FXMLResource;

import java.io.IOException;
import java.util.ResourceBundle;

public class DDatabase extends Application {

    private static Scene scene;

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadFXML(FXMLResource.LOGIN));
        stage.setScene(scene);
        stage.show();
        scene.getWindow().setWidth(1050);
        scene.getWindow().setHeight(600);
        stage.centerOnScreen();
    }

    static void setRoot(FXMLResource fxmlResource) throws IOException {
        scene.setRoot(loadFXML(fxmlResource));
    }

    private static Parent loadFXML(FXMLResource fxmlResource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DDatabase.class.getResource(fxmlResource.getPath()), ResourceBundle.getBundle(fxmlResource.getBundleName()));
        return fxmlLoader.load();
    }

    public static Window getWindow(){
        return scene.getWindow();
    }

    public static Scene getScene(){ return scene;}

    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }

}