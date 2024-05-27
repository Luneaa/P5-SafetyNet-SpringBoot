package com.safetynet.alerts.dto;

import java.util.List;

public class PersonGroupDto {
    public int AdultCount;

    public int ChildCount;

    public List<PersonDto> Persons;

    public PersonGroupDto(List<PersonDto> persons, int adultCount, int childCount) {
        this.Persons = persons;
        this.AdultCount = adultCount;
        this.ChildCount = childCount;
    }
}
