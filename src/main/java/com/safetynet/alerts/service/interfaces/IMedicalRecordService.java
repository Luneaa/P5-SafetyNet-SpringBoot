package com.safetynet.alerts.service.interfaces;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {
    List<MedicalRecord> getMedicalRecords();

    MedicalRecord getMedicalRecord(String firstName, String lastName);

    MedicalRecord addMedicalRecord(MedicalRecord newMedicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);
}
