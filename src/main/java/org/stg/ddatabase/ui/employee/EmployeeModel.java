package org.stg.ddatabase.ui.employee;

import javafx.beans.property.*;

import java.time.LocalDate;

public class EmployeeModel {
    private final IntegerProperty ID;
    private final StringProperty FirstName;
    private final StringProperty LastName;
    private final StringProperty FatherName;
    private final ObjectProperty<LocalDate> RecruitmentDate;
    private final ObjectProperty<LocalDate> UntilDate;
    private final StringProperty AFM;
    private final StringProperty AMKA;
    private final StringProperty PhoneNo;
    private final StringProperty email;
    private final StringProperty IBAN;
    private final IntegerProperty restDays;

    public EmployeeModel() {
        this.ID = new SimpleIntegerProperty();
        this.FirstName = new SimpleStringProperty();
        this.LastName = new SimpleStringProperty();
        this.FatherName = new SimpleStringProperty();
        this.RecruitmentDate = new SimpleObjectProperty<>();
        this.UntilDate = new SimpleObjectProperty<>();
        this.AFM = new SimpleStringProperty();
        this.AMKA = new SimpleStringProperty();
        this.PhoneNo = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.IBAN = new SimpleStringProperty();
        this.restDays = new SimpleIntegerProperty();
    }

    public void setFirstName(String firstName) {
        this.FirstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.LastName.set(lastName);
    }

    public void setFatherName(String fatherName) {
        this.FatherName.set(fatherName);
    }

    public void setAFM(String AFM) {
        this.AFM.set(AFM);
    }

    public void setAMKA(String AMKA) {
        this.AMKA.set(AMKA);
    }

    public void setPhoneNo(String phoneNo) {
        this.PhoneNo.set(phoneNo);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setIBAN(String IBAN) {
        this.IBAN.set(IBAN);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public String getFirstName() {
        return FirstName.get();
    }

    public StringProperty firstNameProperty() {
        return FirstName;
    }

    public String getLastName() {
        return LastName.get();
    }

    public StringProperty lastNameProperty() {
        return LastName;
    }

    public String getFatherName() {
        return FatherName.get();
    }

    public StringProperty fatherNameProperty() {
        return FatherName;
    }

    public LocalDate getRecruitmentDate() {
        return RecruitmentDate.get();
    }

    public ObjectProperty<LocalDate> recruitmentDateProperty() {
        return RecruitmentDate;
    }

    public LocalDate getUntilDate() {
        return UntilDate.get();
    }

    public ObjectProperty<LocalDate> untilDateProperty() {
        return UntilDate;
    }

    public String getAFM() {
        return AFM.get();
    }

    public StringProperty AFMProperty() {
        return AFM;
    }

    public String getAMKA() {
        return AMKA.get();
    }

    public StringProperty AMKAProperty() {
        return AMKA;
    }

    public String getPhoneNo() {
        return PhoneNo.get();
    }

    public StringProperty phoneNoProperty() {
        return PhoneNo;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getIBAN() {
        return IBAN.get();
    }

    public StringProperty IBANProperty() {
        return IBAN;
    }

    public int getRestDays() {
        return restDays.get();
    }

    public IntegerProperty restDaysProperty() {
        return restDays;
    }
}
