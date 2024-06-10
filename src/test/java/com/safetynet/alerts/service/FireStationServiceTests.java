package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.IDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FireStationServiceTests {

    private static FireStationService fireStationService;

    private IDataRepository dataRepository;

    @BeforeEach
    public void setUp()
    {
        dataRepository = Mockito.mock(IDataRepository.class);
        fireStationService = new FireStationService(dataRepository);
    }

    @Test
    void getFireStations() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "2"));

        when(dataRepository.getFireStations()).thenReturn(fireStations);

        var result = fireStationService.getFireStations();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getFireStation() {
        var data = new FireStation("12 Rue du Persil Heureux", "1");

        when(dataRepository.getFireStation(any(String.class))).thenReturn(data);

        var result = fireStationService.getFireStation("12 Rue du Persil Heureux");

        assertNotNull(result);
        assertEquals("12 Rue du Persil Heureux", result.getAddress());
        assertEquals("1", result.getStation());
    }

    @Test
    void getFireStationByStationNumber() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "1"));

        when(dataRepository.getFireStationsByStationNumber(any(String.class))).thenReturn(fireStations);

        var result = fireStationService.getFireStationsByStationNumber("1");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addFireStation() {
        var data = new FireStation("12 Rue du Persil Heureux", "1");
        when(dataRepository.addFireStation(any(FireStation.class))).thenReturn(data);

        var result = fireStationService.addFireStation(new FireStation("12 Rue du Persil Heureux", "1"));
        assertNotNull(result);
        assertEquals("12 Rue du Persil Heureux", result.getAddress());
        assertEquals("1", result.getStation());
    }

    @Test
    void updateFireStation() {
        var data = new FireStation("12 Rue du Persil Heureux", "1");
        when(dataRepository.updateFireStation(any(FireStation.class))).thenReturn(data);

        var result = fireStationService.updateFireStation(new FireStation("12 Rue du Persil Heureux", "1"));
        assertNotNull(result);
        assertEquals("12 Rue du Persil Heureux", result.getAddress());
        assertEquals("1", result.getStation());
    }

    @Test
    void deleteFireStation() {
        fireStationService.deleteFireStation("12 Rue du Persil Heureux", "1");
    }
}
