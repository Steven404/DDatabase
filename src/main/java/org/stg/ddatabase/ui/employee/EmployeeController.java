package org.stg.ddatabase.ui.employee;

import animatefx.animation.FadeInRight;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import static org.stg.ddatabase.api.Token.JWT;

public class EmployeeController {

    @FXML
    AnchorPane sidebarTextPane,sidebarButtonPane,mainPane;

    @FXML
    public void initialize(){
        sidebarTextPane.setPrefWidth(50);
        sidebarButtonPane.setOnMouseClicked(event -> {textPaneGrow();});
    }

    private void textPaneGrow(){
        if (sidebarTextPane.getPrefWidth() == 50){
            sidebarTextPane.setPrefWidth(250);
        }
    }

}
