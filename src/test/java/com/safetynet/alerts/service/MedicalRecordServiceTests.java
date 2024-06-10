package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
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

class MedicalRecordServiceTests {
    private static MedicalRecordService medicalRecordService;

    private IDataRepository dataRepository;

    @BeforeEach
    public void setUp()
    {
        dataRepository = Mockito.mock(IDataRepository.class);
        medicalRecordService = new MedicalRecordService(dataRepository);
    }

    @Test
    void getMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" }));
        medicalRecords.add(new MedicalRecord("Michelle", "Vitesse", "02/03/1945", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" }));

        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecords);

        var result = medicalRecordService.getMedicalRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getMedicalRecord() {
        var data = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        when(dataRepository.getMedicalRecordsForPerson(any(String.class), any(String.class))).thenReturn(data);

        var result = medicalRecordService.getMedicalRecord("John", "Doe");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("12/12/1912", result.getBirthdate());
        assertEquals("paracetamol:350mg", result.getMedications()[0]);
        assertEquals(1, result.getMedications().length);
        assertEquals("peanut", result.getAllergies()[0]);
        assertEquals(1, result.getAllergies().length);
    }

    @Test
    void addMedicalRecord() {
        var data = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        when(dataRepository.addMedicalRecord(any(MedicalRecord.class))).thenReturn(data);

        var result = medicalRecordService.addMedicalRecord(new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" }));

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("12/12/1912", result.getBirthdate());
        assertEquals("paracetamol:350mg", result.getMedications()[0]);
        assertEquals(1, result.getMedications().length);
        assertEquals("peanut", result.getAllergies()[0]);
        assertEquals(1, result.getAllergies().length);
    }

    @Test
    void updateMedicalRecord() {
        var data = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        when(dataRepository.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(data);

        var result = medicalRecordService.updateMedicalRecord(new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" }));

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("12/12/1912", result.getBirthdate());
        assertEquals("paracetamol:350mg", result.getMedications()[0]);
        assertEquals(1, result.getMedications().length);
        assertEquals("peanut", result.getAllergies()[0]);
        assertEquals(1, result.getAllergies().length);
    }

    @Test
    void deleteMedicalRecord() {
        medicalRecordService.deleteMedicalRecord("John", "Doe");
    }
}
