package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class MedicalRecord {

    private String firstName;

    private String lastName;

    private String birthdate;

    private String[] medications;

    private String[] allergies;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String[] getMedications() {
        return medications;
    }

    public String[] getAllergies() {
        return allergies;
    }
}
