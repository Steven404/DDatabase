package org.stg.ddatabase.ui.dialog;

import javafx.scene.control.Alert;
import org.stg.ddatabase.application.DDatabase;

public class Dialog {
    private Alert alert;
    private Alert.AlertType alertType;
    private String header;
    private String contentText;

    public Dialog(Alert.AlertType alertType, String header, String contentText) {
        this.alertType = alertType;
        this.header = header;
        this.contentText = contentText;
        this.alert = new Alert(alertType);
    }

    private void initAlert(){
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.initOwner(DDatabase.getWindow());
    }

    public void show() {
        initAlert();
        alert.show();
    }

    public void showAndWait() {
        initAlert();
        alert.showAndWait();
    }
}

