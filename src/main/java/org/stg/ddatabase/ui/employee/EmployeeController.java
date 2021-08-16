package org.stg.ddatabase.ui.employee;

import com.jfoenix.controls.*;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import org.stg.ddatabase.application.DDatabase;
import org.stg.ddatabase.ui.dialog.DLG;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.stg.ddatabase.ui.employee.EmployeeTableModel.employees;

public class EmployeeController {
    private EmployeeModel employeeModel;
    private final EmployeeService employeeService = new EmployeeService();
    private boolean editMode = false;

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
    JFXButton editSidebarButton, restDaysSidebarButton, searchSidebarButton, resetSidebarButton, insertEmployeeButton,
            updateEmployeeButton, deleteEmployeeButton;


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
        setEmployeeModel();
        sidebarTextPane.setTranslateX(-200);
        darkPane.setDisable(true);
        hamburger.setOnMouseClicked(event -> hamburgerPressed());
        getAllEmployees();
        employeesTable.setOnMouseClicked(event -> tableClicked());
        editSidebarButton.setOnAction(event -> editMode());
        resetSidebarButton.setOnAction(event -> reset());
        insertEmployeeButton.setOnAction(event -> insertEmployee());
        restDaysSidebarButton.setOnAction(event -> updateRestDays());
        deleteEmployeeButton.setOnAction(event -> deleteEmployee());
        searchSidebarButton.setOnAction(event -> searchEmployee());
    }

    private void searchEmployee(){
        employees.sort(Comparator.comparing(EmployeeTableModel::getLastName));
        ChoiceDialog<String> dialog = new ChoiceDialog<>();

        for (EmployeeTableModel employee : employees) {
            dialog.getItems().add(employee.getLastName());
        }

        dialog.setTitle("Αναζήτηση υπαλλήλου");
        dialog.setHeaderText("Αναζήτησ υπαλλήλου με βάση το επίθετο");
        dialog.setContentText("Επιλογή υπαλλήλου:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(this::getEmployee);

    }

    private void getEmployee(String lastName){
        System.out.println(lastName);
    }

    private void deleteEmployee() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Εάν συνεχίσετε, ο υπάλληλος " + employeeModel.getFirstName() + " " + employeeModel.getLastName() +" θα σβηστεί.");
        alert.setContentText("Θέλετε να συνεχίσετε;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteEmployeeTask();
        } else {
            return;
        }

    }

    private void deleteEmployeeTask() {
        if (idTextField.getText().equals("0") || idTextField.getText().equals("") || employeeModel.getID() == 0) {
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Επιλέξτε έναν υπάλληλο και προσπαθήστε ξανά!");
            DLG.WARNING.show();
            return;
        }
        Task<Integer> deleteEmployee = employeeService.deleteEmployee(employeeModel);
        deleteEmployee.setOnSucceeded(workerStateEvent -> {
            switch (deleteEmployee.getValue()) {
                case 200:
                    DLG.INFORMATION.setContentText("Η διαγραφή υπαλλήλου πραγματοποιήθηκε με επιτυχία.");
                    DLG.INFORMATION.show();
                    reset();
                    break;
                case 401:
                    DLG.ERROR.setContentText("Δεν έχετε την εξουσιοδότηση για να διαγράψετε υπαλλήλους!");
                    DLG.ERROR.show();
                    break;
            }
        });
        deleteEmployee.setOnFailed(workerStateEvent -> {
            DLG.ERROR.setContentText("Παρουσιάστηκε άγνωστο σφάλμα");
            DLG.ERROR.show();
            deleteEmployee.getException().printStackTrace();
        });
        Thread thread = new Thread(deleteEmployee);
        thread.setDaemon(true);
        thread.start();
    }

    private void updateRestDays() {
        if (employeeModel.getRestDays() == 0) {
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Ο υπάλληλος που επιλέξατε δεν έχει υπολοιπόμενες άδειες για φέτος!");
            DLG.WARNING.show();
            return;
        }
        if (employeeModel.getFirstName() == null || firstNameTextField.getText().equals("")) {
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Παρακαλώ επιλέξτε ένα υπάλληλο και προσπαθήστε ξανά!");
            DLG.WARNING.show();
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Χορήγηση άδειας");
        dialog.setHeaderText("Χορήγηση άδειας στον/ην υπάλληλο: " + employeeModel.getFirstName() + " " + employeeModel.getLastName());
        dialog.setContentText("Ημέρες Αδείας: ");
        dialog.initOwner(DDatabase.getWindow());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(restDays -> {
            try {
                int restDaysInt;
                restDaysInt = Integer.parseInt(restDays);
                int newRestDays = employeeModel.getRestDays() - restDaysInt;
                System.out.println("New rest days: " + newRestDays);
                System.out.println();
                if (restDaysInt > 25) {
                    DLG.WARNING.setHeader("Προειδοποίηση");
                    DLG.WARNING.setContentText("Οι ημέρες αδείας δεν μπορούν να είναι πάνω από 25!");
                    DLG.WARNING.show();
                    return;
                } else if (restDaysInt > employeeModel.getRestDays()) {
                    DLG.WARNING.setHeader("Προειδοποίηση");
                    DLG.WARNING.setContentText("Οι ημέρες αδείας που προσπαθείτε να χωρηγήσετε " + "\n" +
                            "είναι παραπάνω από τις υπολοιπόμενες άδεις του υπαλλήλου!");
                    DLG.WARNING.show();
                    return;
                } else if (restDaysInt < 1) {
                    DLG.WARNING.setHeader("Προειδοποίηση");
                    DLG.WARNING.setContentText("Οι αριθμός ημερών αδείας δεν μπορεί να είναι μικρότερος του 1!");
                    DLG.WARNING.show();
                    return;
                }
                updateEmployeeRestDays(newRestDays);
            } catch (NumberFormatException e) {
                DLG.ERROR.setHeader("Σφάλμα");
                DLG.ERROR.setContentText("Παρακαλώ συμπληρώστε μόνο αριθμούς στο πεδίο 'Ημέρες άδειας'!");
                DLG.ERROR.show();
            }
        });
    }

    private void insertEmployee() {
        LocalDate recruitDate = recruitmentDatePicker.getValue();
        LocalDate untilDate = untilDatePicker.getValue();
        if (employeeModel.getFirstName().equals("") || employeeModel.getLastName().equals("") || employeeModel.getFatherName().equals("") || untilDate == null ||
                recruitDate == null || employeeModel.getEmail().equals("") || employeeModel.getAFM().equals("") || employeeModel.getAMKA().equals("") ||
                employeeModel.getIBAN().equals("")) {
            DLG.WARNING.setHeader("Προειδοποίηση");
            DLG.WARNING.setContentText("Ένα ή περισσότερα πεδία είναι κενά. Παρακαλώ συμπληρώστε όλα τα πεδία πριν πραγματοποιήσετε την εισαγωγή υπαλλήλου!");
            DLG.WARNING.show();
            return;
        }
        System.out.println(employeeModel.getRecruitmentDate());
        System.out.println(employeeModel.getUntilDate());
        System.out.println(employeeModel.getFirstName());
        Task<Integer> insertEmployeeTask = employeeService.insertEmployee(employeeModel);
        insertEmployeeTask.setOnSucceeded(workerStateEvent -> {
            switch (insertEmployeeTask.getValue()){
                case 201:
                    DLG.INFORMATION.setContentText("Η εισαγωγλη του υπαλλήλου πραγματοποιήθηκε με επιτιχία!");
                    DLG.INFORMATION.show();
                    employeesTable.getItems().clear();
                    getAllEmployees();
            }
        });
        insertEmployeeTask.setOnFailed(workerStateEvent -> {
            insertEmployeeTask.getException().printStackTrace();
        });
        Thread thread = new Thread(insertEmployeeTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void setEmployeeModel() {
        employeeModel = new EmployeeModel();
        idTextField.textProperty().bindBidirectional(employeeModel.IDProperty(), new NumberStringConverter());
        firstNameTextField.textProperty().bindBidirectional(employeeModel.firstNameProperty());
        lastNameTextField.textProperty().bindBidirectional(employeeModel.lastNameProperty());
        fatherNameTextField.textProperty().bindBidirectional(employeeModel.fatherNameProperty());
        recruitmentDatePicker.valueProperty().bindBidirectional(employeeModel.recruitmentDateProperty());
        untilDatePicker.valueProperty().bindBidirectional(employeeModel.untilDateProperty());
        emailTextField.textProperty().bindBidirectional(employeeModel.emailProperty());
        afmTextField.textProperty().bindBidirectional(employeeModel.AFMProperty());
        phoneNoTextField.textProperty().bindBidirectional(employeeModel.phoneNoProperty());
        amkaTextField.textProperty().bindBidirectional(employeeModel.AMKAProperty());
        ibanTextField.textProperty().bindBidirectional(employeeModel.IBANProperty());
        restDaysTextField.textProperty().bindBidirectional(employeeModel.restDaysProperty(), new NumberStringConverter());
    }

    private void reset() {
        getAllEmployees();
        employeesTable.getItems().clear();
        idTextField.setText("");
        firstNameTextField.setEditable(false);
        firstNameTextField.setText("");
        lastNameTextField.setEditable(false);
        lastNameTextField.setText("");
        fatherNameTextField.setEditable(false);
        fatherNameTextField.setText("");
        emailTextField.setEditable(false);
        emailTextField.setText("");
        recruitmentDatePicker.setEditable(false);
        recruitmentDatePicker.setValue(null);
        untilDatePicker.setEditable(false);
        untilDatePicker.setValue(null);
        afmTextField.setEditable(false);
        afmTextField.setText("");
        phoneNoTextField.setEditable(false);
        phoneNoTextField.setText("");
        amkaTextField.setEditable(false);
        amkaTextField.setText("");
        ibanTextField.setEditable(false);
        ibanTextField.setText("");
        restDaysTextField.setText("");
    }

    private void fillTextWithInformation(EmployeeTableModel employeeTableModel){
        idTextField.setText(String.valueOf(employeeTableModel.getID()));
        firstNameTextField.setText(employeeTableModel.getFirstName());
        lastNameTextField.setText(employeeTableModel.getLastName());
        fatherNameTextField.setText(employeeTableModel.getFatherName());
        emailTextField.setText(employeeTableModel.getEmail());
        recruitmentDatePicker.setValue(employeeTableModel.getRecruitmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        untilDatePicker.setValue(employeeTableModel.getUntilDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        afmTextField.setText(employeeTableModel.getAFM());
        phoneNoTextField.setText(employeeTableModel.getPhoneNo());
        amkaTextField.setText(employeeTableModel.getAMKA());
        ibanTextField.setText(employeeTableModel.getIBAN());
        restDaysTextField.setText(String.valueOf(employeeTableModel.getRestDays()));
    }

    private void editMode() {
        if (!editMode) {
            editMode = true;
            DLG.INFORMATION.setHeader("Πληροφορία");
            DLG.INFORMATION.setContentText("Η επεργασία των πεδίων ενεργοποιήθηκε.");
            DLG.INFORMATION.show();
            firstNameTextField.setEditable(true);
            lastNameTextField.setEditable(true);
            fatherNameTextField.setEditable(true);
            emailTextField.setEditable(true);
            recruitmentDatePicker.setEditable(true);
            untilDatePicker.setEditable(true);
            afmTextField.setEditable(true);
            phoneNoTextField.setEditable(true);
            amkaTextField.setEditable(true);
            ibanTextField.setEditable(true);
        } else {
            editMode = false;
            DLG.INFORMATION.setHeader("Πληροφορία");
            DLG.INFORMATION.setContentText("Η επεργασία των πεδίων απενεργοποιήθηκε.");
            DLG.INFORMATION.show();
            firstNameTextField.setEditable(false);
            lastNameTextField.setEditable(false);
            fatherNameTextField.setEditable(false);
            emailTextField.setEditable(false);
            recruitmentDatePicker.setEditable(false);
            untilDatePicker.setEditable(false);
            afmTextField.setEditable(false);
            phoneNoTextField.setEditable(false);
            amkaTextField.setEditable(false);
            ibanTextField.setEditable(false);
        }
    }


    private void tableClicked() {
        if (employeesTable.getSelectionModel().getSelectedItem() == null) {
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

    private void getAllEmployees() {
        Task<Integer> getEmployeesTask = employeeService.getEmployees();
        getEmployeesTask.setOnSucceeded(workerStateEvent -> {
            switch (getEmployeesTask.getValue()) {
                case 200:
                    initializeTreeTableView();
                    break;
                case 500:
                    DLG.ERROR.setHeader("Σφάλμα");
                    DLG.ERROR.setContentText("Παρουσιάστηκε σφάλμα βάσης δεδομένων (κωδικός 500)");
                    DLG.ERROR.show();
                    break;
            }
        });
        getEmployeesTask.setOnFailed(workerStateEvent -> {
            DLG.ERROR.setHeader("Σφάλμα");
            DLG.ERROR.setContentText("Παρουσιάστηκε σφάλμα βάσης δεδομένων (κωδικός 500)");
            DLG.ERROR.show();
        });
        Thread thread = new Thread(getEmployeesTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void updateEmployeeRestDays(int restDays) {
        Task<Integer> updateEmployeeRestDays = employeeService.updateEmployeeRestDays(employeeModel, restDays);
        updateEmployeeRestDays.setOnSucceeded(workerStateEvent -> {
            System.out.println(updateEmployeeRestDays.getValue());
            DLG.INFORMATION.setHeader("Πληροφορία");
            DLG.INFORMATION.setContentText("Η χορήγηση της άδειας πραγματοποιήθηκε με επιτυχεία!");
            DLG.INFORMATION.show();
            employeesTable.getItems().clear();
            getAllEmployees();
        });
        updateEmployeeRestDays.setOnFailed(workerStateEvent -> {
            DLG.ERROR.setContentText("Κάτι πήγε στραβα. Προσπαθήστε ξανά αργότερα");
            updateEmployeeRestDays.getException().printStackTrace();
        });
        Thread thread = new Thread(updateEmployeeRestDays);
        thread.setDaemon(true);
        thread.start();
    }

    private void initializeTreeTableView() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        fatherNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
        recruitmentDateTableColumn.setCellFactory(column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });
        recruitmentDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("RecruitmentDate"));
        untilDateTableColumn.setCellFactory(column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
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
