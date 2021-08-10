package org.stg.ddatabase.ui.dialog;

import com.sun.marlin.DDasher;
import javafx.scene.control.Alert;
import org.stg.ddatabase.application.DDatabase;

public enum DLG {

    ERROR(Alert.AlertType.ERROR,"ERROR",""),
    WARNING(Alert.AlertType.WARNING,"WARNING",""),
    INFORMATION(Alert.AlertType.INFORMATION,"INFORMATION","");

    private final Alert alert;
    private final Alert.AlertType alertType;
    private String header;
    private String contentText;

    DLG(Alert.AlertType alertType, String header, String contentText) {
        this.alertType = alertType;
        this.contentText = contentText;
        this.header = header;
        this.alert = new Alert(alertType);

    }

    public void setAlertOwner(){
        alert.initOwner(DDatabase.getWindow());
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void show(){
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }
}