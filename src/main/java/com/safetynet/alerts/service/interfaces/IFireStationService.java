package com.safetynet.alerts.service.interfaces;

import com.safetynet.alerts.model.FireStation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFireStationService {

    List<FireStation> getFireStations();

    FireStation getFireStation(String address);

    List<FireStation> getFireStationsByStationNumber(String stationNumber);

    FireStation addFireStation(FireStation fireStation);

    FireStation updateFireStation(FireStation fireStation);

    void deleteFireStation(String address, String station);
}
