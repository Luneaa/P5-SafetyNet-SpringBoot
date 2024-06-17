package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StationAlertDto {
    private final String address;

    private final List<StationAlertPersonDto> persons = new ArrayList<>();

    public StationAlertDto(String address, List<StationAlertPersonDto> persons) {
        this.address = address;
        this.persons.addAll(persons);
    }

}
