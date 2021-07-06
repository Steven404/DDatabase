package org.stg.ddatabase.ui.dialog;

import javafx.scene.control.Alert;

public enum DLG {

    ERROR(Alert.AlertType.ERROR,"ERROR",""),
    WARNING(Alert.AlertType.WARNING,"WARNING","");

    private final Alert.AlertType alertType;
    private final String header;
    private final String contentText;

    DLG(Alert.AlertType alertType, String header, String contentText) {

        this.alertType = alertType;
        this.contentText = contentText;
        this.header = header;
    }
}
