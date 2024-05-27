package com.safetynet.alerts.dto;

import java.util.List;

public class ChildAlertChildDto {
    public String FirstName;

    public String LastName;

    public int Age;

    public List<ChildAlertFamilyMemberDto> FamilyMembers;

    public ChildAlertChildDto(String firstName, String lastName, int age, List<ChildAlertFamilyMemberDto> familyMembers){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Age = age;
        this.FamilyMembers = familyMembers;
    }
}
