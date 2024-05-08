package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IDataRepository;
import com.safetynet.alerts.service.interfaces.IMedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicalRecordService implements IMedicalRecordService {
    private final IDataRepository repository;

    public MedicalRecordService(IDataRepository repository) {
        this.repository = repository;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return this.repository.getMedicalRecords();
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName){
        return this.repository.getMedicalRecordsForPerson(firstName, lastName);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord newMedicalRecord) {
        return this.repository.addMedicalRecord(newMedicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        return this.repository.updateMedicalRecord(medicalRecord);
    }


    public void deleteMedicalRecord(String firstName, String lastName) {
        this.repository.deleteMedicalRecord(firstName, lastName);
    }
}
