package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PersonGroupDto {

    private final int adultCount;

    private final int childCount;

    private final List<PersonDto> persons = new ArrayList<>();

    public PersonGroupDto(List<PersonDto> persons, int adultCount, int childCount) {
        this.persons.addAll(persons);
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

}
