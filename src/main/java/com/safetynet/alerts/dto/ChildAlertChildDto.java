package com.safetynet.alerts.dto;

import lombok.Getter;

@Getter
public class ChildAlertChildDto {

    private final String firstName;

    private final String lastName;

    private final int age;

    public ChildAlertChildDto(String firstName, String lastName, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

}
