package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.interfaces.IFireStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    private final IFireStationService fireStationService;

    public FireStationController(IFireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/fireStations/")
    public ResponseEntity<List<FireStation>> getFireStations() {
        var fireStations = fireStationService.getFireStations();

        if (fireStations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(fireStations, HttpStatus.OK);
    }

    @GetMapping("/fireStations/{address}")
    public ResponseEntity<FireStation> getFireStation(@PathVariable("address") String address){
        var fireStation = fireStationService.getFireStation(address);

        if (fireStation == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(fireStation, HttpStatus.OK);
    }

    @PostMapping("/fireStations/")
    public ResponseEntity<FireStation> postFireStation(@RequestBody FireStation fireStation){
        var newFireStation = fireStationService.addFireStation(fireStation);

        return new ResponseEntity<>(newFireStation, HttpStatus.OK);
    }

    @PutMapping("/fireStations/")
    public ResponseEntity<FireStation> putFireStation(@RequestBody FireStation fireStation){
        var updatedFireStation = fireStationService.updateFireStation(fireStation);

        return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
    }

    @DeleteMapping("/fireStations/{address}/{station}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable ("address") String address, @PathVariable("station") String station){
        fireStationService.deleteFireStation(address, station);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
