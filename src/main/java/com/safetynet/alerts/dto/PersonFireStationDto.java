package com.safetynet.alerts.dto;

public class PersonFireStationDto {
    public String FirstName;

    public String LastName;

    public String PhoneNumber;

    public int Age;

    public String[] Medications;

    public String[] Allergies;

    public PersonFireStationDto(String firstName, String lastName, String phoneNumber, int age, String[] medications, String[] allergies) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.Age = age;
        this.Medications = medications;
        this.Allergies = allergies;
    }
}
