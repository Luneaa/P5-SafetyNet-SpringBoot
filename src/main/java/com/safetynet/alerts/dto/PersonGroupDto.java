package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PersonGroupDto {

    private final int adultCount;

    private final int childCount;

    private final List<PersonDto> persons;

    public PersonGroupDto(List<PersonDto> persons, int adultCount, int childCount) {
        this.persons = persons;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

}
