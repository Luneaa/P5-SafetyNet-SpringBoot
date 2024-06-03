package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FireStationPersonListDto {

    private final String number;

    private final List<PersonFireStationDto> persons;

    public FireStationPersonListDto(String number, List<PersonFireStationDto> persons){
        this.number = number;
        this.persons = persons;
    }

}
