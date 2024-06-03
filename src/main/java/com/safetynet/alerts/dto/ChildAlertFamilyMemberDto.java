package com.safetynet.alerts.dto;

import lombok.Getter;

@Getter
public class ChildAlertFamilyMemberDto {

    private final String firstName;

    private final String lastName;

    public ChildAlertFamilyMemberDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
