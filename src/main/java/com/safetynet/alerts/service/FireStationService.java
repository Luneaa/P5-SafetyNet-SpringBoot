package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.IDataRepository;
import com.safetynet.alerts.service.interfaces.IFireStationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService implements IFireStationService {

    private final IDataRepository repository;

    public FireStationService(IDataRepository repository) {
        this.repository = repository;
    }

    public List<FireStation> getFireStations() {
        return this.repository.getFireStations();
    }

    public FireStation getFireStation(String address){
        return this.repository.getFireStation(address);
    }

    public List<FireStation> getFireStationsByStationNumber(String stationNumber) {
        return this.repository.getFireStationsByStationNumber(stationNumber);
    }

    public FireStation addFireStation(FireStation newFireStation) {
        return this.repository.addFireStation(newFireStation);
    }

    public FireStation updateFireStation(FireStation fireStation) {
        return this.repository.udpateFireStation(fireStation);
    }


    public void deleteFireStation(String firstName, String lastName) {
        this.repository.deleteFireStation(firstName, lastName);
    }

}
