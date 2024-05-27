package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getFireStations() throws Exception {
        // Call "/fireStations" and get a list of fireStations with code 200

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "2"));

        when(fireStationService.getFireStations()).thenReturn(fireStations);

        mockMvc.perform(get("/fireStations/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getFireStationsNoContent() throws Exception {
        // Call "/fireStations" and get no content code
        when (fireStationService.getFireStations()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/fireStations/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getFireStation() throws Exception {
        // Call "/fireStations/{address}" and get a fireStation
        var fireStation = new FireStation("12 Rue du Persil Heureux", "1");
        when(fireStationService.getFireStation(any(String.class))).thenReturn(fireStation);

        mockMvc.perform(get("/fireStations/{address}", "12 Rue du Persil Heureux", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addFireStation() throws Exception {
        var newFireStation = new FireStation("12 Rue du Persil Heureux", "1");
        when(fireStationService.addFireStation(any(FireStation.class))).thenReturn(newFireStation);

        mockMvc.perform(post("/fireStations/", newFireStation)
                        .content(objectMapper.writeValueAsString(newFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFireStation() throws Exception {
        FireStation updateFireStation = new FireStation("12 Rue du Persil Heureux", "1");
        when(fireStationService.updateFireStation(any(FireStation.class))).thenReturn(updateFireStation);

        mockMvc.perform(put("/fireStations/", updateFireStation)
                        .content(objectMapper.writeValueAsString(updateFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFireStation() throws Exception {
        mockMvc.perform(delete("/fireStations/{address}/{station}", "12 Rue du Persil Heureux", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
