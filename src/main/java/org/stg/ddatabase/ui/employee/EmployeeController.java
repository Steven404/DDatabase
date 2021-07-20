package org.stg.ddatabase.ui.employee;

import com.jfoenix.controls.*;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.stg.ddatabase.api.Authentication;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.stg.ddatabase.ui.employee.EmployeeModel.employees;

public class EmployeeController {
    private EmployeeService employeeService = new EmployeeService();

    @FXML
    JFXHamburger hamburger;

    @FXML
    AnchorPane sidebarTextPane, sidebarButtonPane, mainPane;

    @FXML
    JFXButton updateEmployeeButton;

    @FXML
    JFXButton insertEmployeeButton;

    TranslateTransition translateTransition;

    @FXML
    TableView<EmployeeModel> employeesTable;

    @FXML
    TableColumn<EmployeeModel, Integer> IDTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> firstNameTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> lastNameTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> fatherNameTableColumn;

    @FXML
    TableColumn<EmployeeModel, Date> recruitmentDateTableColumn;

    @FXML
    TableColumn<EmployeeModel, Date> untilDateTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> AFMTableColumn;

    @FXML
    TableColumn<EmployeeModel, String> AMKATableColumn;

    @FXML
    TableColumn<EmployeeModel, String> PhoneNoTableColumn;


    @FXML
    public void initialize() {
        sidebarTextPane.setTranslateX(-200);
        hamburger.setOnMouseClicked(event -> {
            hamburgerPressed();
        });
        runEmployeeService();
        initializeTreeTableView();
    }

    private void hamburgerPressed() {
        if (sidebarTextPane.getTranslateX() == 0) {
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(-200);
            translateTransition.play();
        } else if (sidebarTextPane.getTranslateX() == -200) {
            translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebarTextPane);
            translateTransition.setByX(+200);
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

        employeesTable.setItems(employees);
    }

}
