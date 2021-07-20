package org.stg.ddatabase.ui.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class EmployeeModel {
    private int ID;
    private String FirstName;
    private String LastName;
    private String FatherName;
    private Date RecruitmentDate;
    private Date UntilDate;
    private String AFM;
    private String AMKA;
    private String PhoneNo;
    private String email;
    private String IBAN;

    static ObservableList<EmployeeModel> employees = FXCollections.observableArrayList();

    public EmployeeModel(int ID, String firstName, String lastName, String fatherName, Date recruitmentDate, Date untilDate, String AFM, String AMKA, String phoneNo, String email, String IBAN) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        FatherName = fatherName;
        RecruitmentDate = recruitmentDate;
        UntilDate = untilDate;
        this.AFM = AFM;
        this.AMKA = AMKA;
        PhoneNo = phoneNo;
        this.email = email;
        this.IBAN = IBAN;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public Date getRecruitmentDate() {
        return RecruitmentDate;
    }

    public Date getUntilDate() {
        return UntilDate;
    }

    public String getAFM() {
        return AFM;
    }

    public String getAMKA() {
        return AMKA;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getIBAN() {
        return IBAN;
    }
}
