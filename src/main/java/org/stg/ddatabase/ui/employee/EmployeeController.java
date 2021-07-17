package org.stg.ddatabase.ui.employee;

import animatefx.animation.FadeInRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import static org.stg.ddatabase.api.Token.JWT;

public class EmployeeController {
    @FXML
    JFXHamburger hamburger;

    @FXML
    AnchorPane sidebarTextPane,sidebarButtonPane,mainPane;

    @FXML
    JFXButton allEmployeesButton;

    @FXML
    JFXButton insertEmployeeButton;

    TranslateTransition translateTransition;

    @FXML
    public void initialize(){
        sidebarTextPane.setTranslateX(-200);
        hamburger.setOnMouseClicked(event -> {textPaneGrow();});
    }

    private void textPaneGrow(){
        if (sidebarTextPane.getTranslateX() == 0){
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(-200);
            translateTransition.play();
        } else if(sidebarTextPane.getTranslateX() == -200){
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(+200);
            translateTransition.play();
        }

    }

}
