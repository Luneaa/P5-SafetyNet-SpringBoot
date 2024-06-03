package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StationAlertDto {
    private final String address;

    private final List<StationAlertPersonDto> persons;

    public StationAlertDto(String address, List<StationAlertPersonDto> persons) {
        this.address = address;
        this.persons = persons;
    }

}
