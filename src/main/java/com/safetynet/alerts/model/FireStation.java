package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class FireStation {

    private String address;

    private String station;

    public String getAddress() {
        return address;
    }

    public String getStation() {
        return station;
    }
}
