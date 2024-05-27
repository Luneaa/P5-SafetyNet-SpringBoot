package com.safetynet.alerts.dto;

import java.util.List;

public class FireStationPersonListDto {
    public String Number;

    public List<PersonFireStationDto> Persons;

    public FireStationPersonListDto(String number, List<PersonFireStationDto> persons){
        this.Number = number;
        this.Persons = persons;
    }
}
