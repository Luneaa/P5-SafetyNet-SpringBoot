package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.interfaces.IMedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    private final IMedicalRecordService medicalRecordService;

    public MedicalRecordController(IMedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecords")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords() {
        var medicalRecords = medicalRecordService.getMedicalRecords();

        if (medicalRecords.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        var medicalRecord = medicalRecordService.getMedicalRecord(firstName, lastName);

        if (medicalRecord == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @PostMapping("/medicalRecords")
    public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        var newMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);

        return new ResponseEntity<>(newMedicalRecord, HttpStatus.OK);
    }

    @PutMapping("/medicalRecords")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        var updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);

        return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
    }

    @DeleteMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable ("firstName") String firstName, @PathVariable("lastName") String lastName){
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
