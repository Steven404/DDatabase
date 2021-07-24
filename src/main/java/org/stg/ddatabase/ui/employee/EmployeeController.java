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
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.stg.ddatabase.api.Authentication;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import static org.stg.ddatabase.ui.employee.EmployeeTableModel.employees;

public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();

    @FXML
    JFXTextField idTextField, firstNameTextField, lastNameTextField, fatherNameTextField,
            emailTextField, afmTextField, phoneNoTextField, amkaTextField, ibanTextField, restDaysTextField;

    @FXML
    DatePicker recruitmentDatePicker, untilDatePicker;

    @FXML
    JFXHamburger hamburger;

    @FXML
    AnchorPane sidebarTextPane, sidebarButtonPane, mainPane, darkPane;

    @FXML
    JFXButton updateEmployeeButton;


    TranslateTransition translateTransition;

    @FXML
    TableView<EmployeeTableModel> employeesTable;

    @FXML
    TableColumn<EmployeeTableModel, Integer> idTableColumn;

    @FXML
    TableColumn<EmployeeTableModel, String> firstNameTableColumn, lastNameTableColumn, fatherNameTableColumn,
            AFMTableColumn, AMKATableColumn, PhoneNoTableColumn, EmailTableColumn, IBANTableColumn;

    @FXML
    TableColumn<EmployeeTableModel, Integer> restDaysTableColumn;

    @FXML
    TableColumn<EmployeeTableModel, Date> recruitmentDateTableColumn, untilDateTableColumn;

    @FXML
    VBox vbox;

    @FXML
    public void initialize() {
        sidebarTextPane.setTranslateX(-200);
        darkPane.setDisable(true);
        hamburger.setOnMouseClicked(event -> hamburgerPressed());
        runEmployeeService();
        initializeTreeTableView();
        employeesTable.setOnMouseClicked(event -> tableClicked());
    }


    private void tableClicked() {
        if (employeesTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        EmployeeTableModel employeeTableModel = employeesTable.getSelectionModel().getSelectedItem();
        idTextField.setText(String.valueOf(employeeTableModel.getID()));
        firstNameTextField.setText(employeeTableModel.getFirstName());
        lastNameTextField.setText(employeeTableModel.getLastName());
        fatherNameTextField.setText(employeeTableModel.getFatherName());
        recruitmentDatePicker.setValue(employeeTableModel.getRecruitmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        untilDatePicker.setValue(employeeTableModel.getUntilDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        phoneNoTextField.setText(employeeTableModel.getPhoneNo());
        emailTextField.setText(employeeTableModel.getEmail());
        afmTextField.setText(employeeTableModel.getAFM());
        amkaTextField.setText(employeeTableModel.getAMKA());
        ibanTextField.setText(employeeTableModel.getIBAN());
        restDaysTextField.setText(String.valueOf(employeeTableModel.getRestDays()));
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
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        fatherNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        recruitmentDateTableColumn.setCellFactory(column -> {
            TableCell<EmployeeTableModel, Date> cell = new TableCell<EmployeeTableModel, Date>() {
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
            TableCell<EmployeeTableModel, Date> cell = new TableCell<EmployeeTableModel, Date>() {
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
        restDaysTableColumn.setCellValueFactory(new PropertyValueFactory<>("restDays"));

        employeesTable.setItems(employees);
    }

}
