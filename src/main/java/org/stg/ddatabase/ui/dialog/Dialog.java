package org.stg.ddatabase.ui.dialog;

import javafx.scene.control.Alert;
import org.stg.ddatabase.application.DDatabase;

public class Dialog {
    private Alert.AlertType alertType;
    private String header;
    private String contentText;

    public Dialog(Alert.AlertType alertType, String header, String contentText) {
        this.alertType = alertType;
        this.header = header;
        this.contentText = contentText;
    }
    public void show(){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.initOwner(DDatabase.getWindow());
        alert.show();
        }
    }

