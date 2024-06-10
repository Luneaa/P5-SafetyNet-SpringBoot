package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonRepositoryTests {

    private static JsonRepository jsonRepository;

    @BeforeEach
    public void setUp() throws IOException { jsonRepository = new JsonRepository(); }

    @Test
    void addPerson() {
        var newPerson = jsonRepository.addPerson(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        assertNotNull(newPerson);
        assertEquals("John", newPerson.getFirstName());
        assertEquals("Doe", newPerson.getLastName());
        assertEquals("12 Rue du Persil Heureux", newPerson.getAddress());
        assertEquals("Pau", newPerson.getCity());
        assertEquals("1234", newPerson.getZip());
        assertEquals("+33456787512", newPerson.getPhone());
        assertEquals("johndoe@gmail.com", newPerson.getEmail());
    }

    @Test
    void addPersonAlreadyExists() {
        var newPerson = jsonRepository.addPerson(new Person("John", "Boyd", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "jaboyd@email.com"));

        assertNull(newPerson);
    }

    @Test
    void getPersons(){
        assertNotNull(jsonRepository.getPersons());
    }

    @Test
    void getPerson() {
        var person = jsonRepository.getPerson("John", "Boyd", "jaboyd@email.com");

        assertNotNull(person);
    }

    @Test
    void getPersonNotFound() {
        var person = jsonRepository.getPerson("John", "Doe", "404@notfound.com");

        assertNull(person);
    }

    @Test
    void updatePerson() {
        var personToUpdate = new Person("John", "Boyd","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "jaboyd@email.com");

        var updatedPerson = jsonRepository.updatePerson(personToUpdate);

        assertNotNull(updatedPerson);
        assertEquals("John", updatedPerson.getFirstName());
        assertEquals("Boyd", updatedPerson.getLastName());
        assertEquals("12 Rue du Persil Heureux", updatedPerson.getAddress());
        assertEquals("Pau", updatedPerson.getCity());
        assertEquals("1234", updatedPerson.getZip());
        assertEquals("+33456787512", updatedPerson.getPhone());
        assertEquals("jaboyd@email.com", updatedPerson.getEmail());
    }

    @Test
    void updatePersonNotFound() {
        var personToUpdate = new Person("Didier", "Vitesse","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "didiervitesse@email.com");
        var updatedPerson = jsonRepository.updatePerson(personToUpdate);

        assertNull(updatedPerson);
    }

    @Test
    void updatePersonNotFoundFirstNameMatch() {
        var personToUpdate = new Person("John", "Vitesse","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "didiervitesse@email.com");
        var updatedPerson = jsonRepository.updatePerson(personToUpdate);

        assertNull(updatedPerson);
    }

    @Test
    void updatePersonNotFoundLastNameMatch() {
        var personToUpdate = new Person("Didier", "Boyd","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "didiervitesse@email.com");
        var updatedPerson = jsonRepository.updatePerson(personToUpdate);

        assertNull(updatedPerson);
    }

    @Test
    void deletePerson() {
        // Get existing person
        assertNotNull(jsonRepository.getPerson("John", "Boyd", "jaboyd@email.com"));

        // Delete
        jsonRepository.deletePerson("John", "Boyd");

        // Get again to check that person is deleted
        assertNull(jsonRepository.getPerson("John", "Boyd", "jaboyd@email.com"));
    }

    @Test
    void deletePersonNotExists() {
        assertNull(jsonRepository.getPerson("Didier", "Vitesse", "didiervitesse@mail.com"));

        jsonRepository.deletePerson("Didier", "Vitesse");
    }

    @Test
    void getFireStations() {
        assertEquals(13, (long) jsonRepository.getFireStations().size());
    }

    @Test
    void getFireStation() {
        var fireStation = jsonRepository.getFireStation("1509 Culver St");

        assertEquals("3", fireStation.getStation());
        assertEquals("1509 Culver St", fireStation.getAddress());
    }

    @Test
    void getFireStationByStationNumber() {
        var fireStations = jsonRepository.getFireStationsByStationNumber("1");

        assertEquals(3, fireStations.size());
    }

    @Test
    void addFireStation() {
        var newFireStation = new FireStation("12 Rue du Persil Heureux", "1");

        var addedFireStation = jsonRepository.addFireStation(newFireStation);

        assertNotNull(addedFireStation);
        assertEquals("12 Rue du Persil Heureux", addedFireStation.getAddress());
        assertEquals("1", addedFireStation.getStation());
    }

    @Test
    void addFireStationAlreadyExists() {
        var newFireStation = new FireStation("1509 Culver St", "3");

        var addedFireStation = jsonRepository.addFireStation(newFireStation);

        assertNull(addedFireStation);
    }

    @Test
    void updateFireStation() {
        var existingFireStation = new FireStation("1509 Culver St", "2");

        var updatedFireStation = jsonRepository.updateFireStation(existingFireStation);

        assertNotNull(updatedFireStation);
        assertEquals("1509 Culver St", updatedFireStation.getAddress());
        assertEquals("2", updatedFireStation.getStation());
    }

    @Test
    void updateFireStationNotExists() {
        var fireStation = new FireStation("12 Rue des Tulipes", "10");

        var updatedFireStation = jsonRepository.updateFireStation(fireStation);

        assertNull(updatedFireStation);
    }

    @Test
    void deleteFireStation() {
        // Get fire station
        assertNotNull(jsonRepository.getFireStation("1509 Culver St"));

        // Delete fire station
        jsonRepository.deleteFireStation("1509 Culver St", "3");

        // Check that it is deleted
        assertNull(jsonRepository.getFireStation("1509 Culver St"));
    }

    @Test
    void deleteFireStationNotExists() {
        assertNull(jsonRepository.getFireStation("12 Rue des Tulipes"));

        jsonRepository.deleteFireStation("12 Rue des Tulipes", "23");
    }

    @Test
    void addMedicalRecord() {
        var newMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        var addedMedicalRecord = jsonRepository.addMedicalRecord(newMedicalRecord);

        assertNotNull(addedMedicalRecord);
        assertEquals("John", addedMedicalRecord.getFirstName());
        assertEquals("Doe", addedMedicalRecord.getLastName());
        assertEquals("12/12/1912", addedMedicalRecord.getBirthdate());
        assertEquals("paracetamol:350mg", addedMedicalRecord.getMedications()[0]);
        assertEquals(1, addedMedicalRecord.getMedications().length);
        assertEquals("peanut", addedMedicalRecord.getAllergies()[0]);
        assertEquals(1, addedMedicalRecord.getAllergies().length);
    }

    @Test
    void addMedicalRecordAlreadyExists() {
        var alreadyExistsMedicalRecord = new MedicalRecord("John", "Boyd", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        var addedMedicalRecord = jsonRepository.addMedicalRecord(alreadyExistsMedicalRecord);

        assertNull(addedMedicalRecord);
    }

    @Test
    void getMedicalRecords() {
        assertEquals(23, jsonRepository.getMedicalRecords().size());
    }

    @Test
    void getMedicalRecordsForPerson() {
        var medicalRecord = jsonRepository.getMedicalRecordsForPerson("John", "Boyd");

        assertNotNull(medicalRecord);

        assertEquals("03/06/1984", medicalRecord.getBirthdate());
        assertEquals("aznol:350mg", medicalRecord.getMedications()[0]);
        assertEquals("hydrapermazol:100mg", medicalRecord.getMedications()[1]);
        assertEquals(2, medicalRecord.getMedications().length);
        assertEquals("nillacilan", medicalRecord.getAllergies()[0]);
        assertEquals(1, medicalRecord.getAllergies().length);
    }

    @Test
    void getMedicalRecordsForPersonNotExists() {
        var medicalRecord = jsonRepository.getMedicalRecordsForPerson("Didier", "Vitesse");

        assertNull(medicalRecord);
    }

    @Test
    void getMedicalRecordsForPersonNotExistsSameFirstName() {
        var medicalRecord = jsonRepository.getMedicalRecordsForPerson("John", "Vitesse");

        assertNull(medicalRecord);
    }

    @Test
    void getMedicalRecordsForPersonNotExistsSameLastName() {
        var medicalRecord = jsonRepository.getMedicalRecordsForPerson("Didier", "Boyd");

        assertNull(medicalRecord);
    }

    @Test
    void updateMedicalRecord() {
        var alreadyExistsMedicalRecord = new MedicalRecord("John", "Boyd", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });

        var updatedMedicalRecord = jsonRepository.updateMedicalRecord(alreadyExistsMedicalRecord);

        assertNotNull(updatedMedicalRecord);
        assertEquals("12/12/1912", updatedMedicalRecord.getBirthdate());
        assertEquals("paracetamol:350mg", updatedMedicalRecord.getMedications()[0]);
        assertEquals(1, updatedMedicalRecord.getMedications().length);
        assertEquals("peanut", updatedMedicalRecord.getAllergies()[0]);
        assertEquals(1, updatedMedicalRecord.getAllergies().length);
    }

    @Test
    void updateMedicalRecordNotExists() {
        var updatedMedicalRecord = jsonRepository.updateMedicalRecord(new MedicalRecord("Didier", "Vitesse", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" }));

        assertNull(updatedMedicalRecord);
    }

    @Test
    void deleteMedicalRecord() {
        assertNotNull(jsonRepository.getMedicalRecordsForPerson("John", "Boyd"));

        jsonRepository.deleteMedicalRecord("John", "Boyd");

        assertNull(jsonRepository.getMedicalRecordsForPerson("John", "Boyd"));
    }

    @Test
    void deleteMedicalRecordNotExists() {
        assertNull(jsonRepository.getMedicalRecordsForPerson("Didier", "Vitesse"));

        jsonRepository.deleteMedicalRecord("Didier", "Vitesse");
    }
}
