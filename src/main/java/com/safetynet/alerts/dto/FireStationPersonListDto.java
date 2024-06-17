package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FireStationPersonListDto {

    private final String number;

    private final List<PersonFireStationDto> persons = new ArrayList<>();

    public FireStationPersonListDto(String number, List<PersonFireStationDto> persons){
        this.number = number;
        this.persons.addAll(persons);
    }

}
