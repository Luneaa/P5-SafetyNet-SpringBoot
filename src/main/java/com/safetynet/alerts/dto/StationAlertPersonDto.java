package com.safetynet.alerts.dto;

import lombok.Getter;

@Getter
public class StationAlertPersonDto {
    private final String firstName;

    private final String lastName;

    private final String phoneNumber;

    private final int age;

    private final String[] medications;

    private final String[] allergies;

    public StationAlertPersonDto(String firstName, String lastName, String phoneNumber, int age, String[] medications, String[] allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}
