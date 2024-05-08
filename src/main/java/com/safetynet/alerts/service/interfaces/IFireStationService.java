package com.safetynet.alerts.service.interfaces;

//import lombok.Data;
import com.safetynet.alerts.model.FireStation;
import org.springframework.stereotype.Service;

import java.util.List;

//@Data
@Service
public interface IFireStationService {

    List<FireStation> getFireStations();

    FireStation getFireStation(String address);

    FireStation addFireStation(FireStation fireStation);

    FireStation updateFireStation(FireStation fireStation);

    void deleteFireStation(String firstName, String lastName);
}
