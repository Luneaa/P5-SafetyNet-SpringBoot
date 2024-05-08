package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
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

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMedicalRecords() throws Exception {
        // Call "/medicalRecords" and get a list of medicalRecords with code 200

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" }));
        medicalRecords.add(new MedicalRecord("Michelle", "Vitesse", "02/03/1945", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" }));

        when(medicalRecordService.getMedicalRecords()).thenReturn(medicalRecords);

        mockMvc.perform(get("/medicalRecords").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getMedicalRecordsNoContent() throws Exception {
        // Call "/medicalRecords" and get no content code
        when (medicalRecordService.getMedicalRecords()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/medicalRecords").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getMedicalRecord() throws Exception {
        // Call "/medicalRecords/{firstName}/{lastName}" and get a medicalRecord
        var medicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        when(medicalRecordService.getMedicalRecord(any(String.class), any(String.class))).thenReturn(medicalRecord);

        mockMvc.perform(get("/medicalRecords/{firstName}/{lastName}", "John", "Doe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addMedicalRecord() throws Exception {
        var newMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class))).thenReturn(newMedicalRecord);

        mockMvc.perform(post("/medicalRecords", newMedicalRecord)
                        .content(objectMapper.writeValueAsString(newMedicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateMedicalRecord() throws Exception {
        MedicalRecord updateMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(updateMedicalRecord);

        mockMvc.perform(put("/medicalRecords", updateMedicalRecord)
                        .content(objectMapper.writeValueAsString(updateMedicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecords/{firstName}/{lastName}", "John", "Doe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
