package org.stg.ddatabase.ui.employee;

import com.jfoenix.controls.*;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.stg.ddatabase.api.Authentication;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.stg.ddatabase.ui.employee.EmployeeModel.employees;

public class EmployeeController {
    private EmployeeService employeeService = new EmployeeService();
    private EmployeeModel employeeModel;

    @FXML
    JFXTextField IDTextField,FirstNameTextField,LastNameTextField,FatherNameTextField;

    @FXML
    DatePicker RecruitmentDatePicker,UntilDatePicker;

    @FXML
    JFXHamburger hamburger;

    @FXML
    AnchorPane sidebarTextPane, sidebarButtonPane, mainPane, darkPane;

    @FXML
    JFXButton updateEmployeeButton,EditButton;


    TranslateTransition translateTransition;

    @FXML
    TableView<EmployeeModel> employeesTable;

    @FXML
    TableColumn<EmployeeModel, Integer> IDTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> firstNameTableColumn,lastNameTableColumn,fatherNameTableColumn,AFMTableColumn,AMKATableColumn,PhoneNoTableColumn,EmailTableColumn,IBANTableColumn;

    @FXML
    TableColumn<EmployeeModel, Date> recruitmentDateTableColumn, untilDateTableColumn;

    @FXML
    public void initialize() {
        sidebarTextPane.setTranslateX(-200);
        darkPane.setDisable(true);
        hamburger.setOnMouseClicked(event -> {
            hamburgerPressed();
        });
        runEmployeeService();
        initializeTreeTableView();
        employeesTable.setOnMouseClicked(event -> tableClicked());
        EditButton.setOnAction(event -> editMode());
    }

    private void editMode(){
        if (employeesTable.getTranslateX() == 320) {
            translateTransition = new TranslateTransition(Duration.seconds(0.5), employeesTable);
            translateTransition.setByY(+110);
            translateTransition.play();
        } else if (employeesTable.getTranslateX() == 430){
            translateTransition = new TranslateTransition(Duration.seconds(0.5), employeesTable);
            translateTransition.setByY(-110);
            translateTransition.play();
        }
    }

    private void tableClicked(){
        employeeModel = employeesTable.getSelectionModel().getSelectedItem();

        IDTextField.setText(String.valueOf(employeeModel.getID()));
        FirstNameTextField.setText(employeeModel.getFirstName());
        LastNameTextField.setText(employeeModel.getLastName());
        FatherNameTextField.setText(employeeModel.getFatherName());
        RecruitmentDatePicker.setValue(employeeModel.getRecruitmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        UntilDatePicker.setValue(employeeModel.getUntilDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private void hamburgerPressed() {
        if (sidebarTextPane.getTranslateX() == 0) {
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(-200);
            darkPane.setDisable(true);
            darkPane.setOpacity(0);
            translateTransition.play();

        } else if (sidebarTextPane.getTranslateX() == -200) {
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(+200);
            darkPane.setDisable(false);
            darkPane.setOpacity(0.5);
            translateTransition.play();
        }

    }

    private void runEmployeeService() {
        Task<String> getEmployeesTask = employeeService.getEmployees(Authentication.getToken());
        getEmployeesTask.setOnSucceeded(workerStateEvent -> {
            System.out.println("Successful thread run");
        });
        getEmployeesTask.setOnFailed(workerStateEvent -> {
            System.out.println("Task failed to run");
        });
        Thread thread = new Thread(getEmployeesTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initializeTreeTableView() {
        IDTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        fatherNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        recruitmentDateTableColumn.setCellFactory(column -> {
            TableCell<EmployeeModel, Date> cell = new TableCell<EmployeeModel, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        recruitmentDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("RecruitmentDate"));
        untilDateTableColumn.setCellFactory(column -> {
            TableCell<EmployeeModel, Date> cell = new TableCell<EmployeeModel, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        untilDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UntilDate"));
        AFMTableColumn.setCellValueFactory(new PropertyValueFactory<>("AFM"));
        AMKATableColumn.setCellValueFactory(new PropertyValueFactory<>("AMKA"));
        PhoneNoTableColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNo"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        IBANTableColumn.setCellValueFactory(new PropertyValueFactory<>("IBAN"));

        employeesTable.setItems(employees);
    }

}
