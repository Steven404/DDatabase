package org.stg.ddatabase.ui.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class EmployeeTableModel {
    private final int ID;
    private final String FirstName;
    private final String LastName;
    private final String FatherName;
    private final Date RecruitmentDate;
    private final Date UntilDate;
    private final String AFM;
    private final String AMKA;
    private final String PhoneNo;
    private final String email;
    private final String IBAN;
    private final int restDays;

    static ObservableList<EmployeeTableModel> employees = FXCollections.observableArrayList();

    public EmployeeTableModel(int ID, String firstName, String lastName, String fatherName, Date recruitmentDate, Date untilDate, String AFM, String AMKA, String phoneNo, String email, String IBAN, int restDays) {
        this.ID = ID;
        this.FirstName = firstName;
        LastName = lastName;
        FatherName = fatherName;
        RecruitmentDate = recruitmentDate;
        UntilDate = untilDate;
        this.AFM = AFM;
        this.AMKA = AMKA;
        PhoneNo = phoneNo;
        this.email = email;
        this.IBAN = IBAN;
        this.restDays = restDays;
    }

    public int getRestDays() {
        return restDays;
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
