package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface IDataRepository {

    Person addPerson(Person person);

    List<Person> getPersons();

    Person getPerson(String firstName, String lastName);

    Person udpatePerson(Person person);

    void deletePerson(String firstName, String lastName);

    List<FireStation> getFireStations();

    FireStation getFireStation(String address, String station);

    FireStation addFireStation(FireStation newFireStation);

    FireStation udpateFireStation(FireStation fireStation);

    void deleteFireStation(String address, String station);

    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

    List<MedicalRecord> getMedicalRecords();

    MedicalRecord getMedicalRecordsForPerson(String firstName, String lastName);

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);
}
