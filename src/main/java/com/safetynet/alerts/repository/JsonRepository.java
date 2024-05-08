package com.safetynet.alerts.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JsonRepository implements IDataRepository {

    private List<Person> persons;

    private List<FireStation> fireStations;

    private List<MedicalRecord> medicalRecords;

    public JsonRepository() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Set date format to correct date format
        mapper.registerModule(new JavaTimeModule());
        JsonFormat.Value dateFormat = JsonFormat.Value.forPattern("MM/dd/yyyy");
        mapper.configOverride(LocalDate.class).setFormat(dateFormat);

        // Read json data file
        File dataFile = new File("src/main/resources/data.json");
        JsonNode root = mapper.readTree(dataFile);

        // Extract three collections (persons, fire stations, medical records)
        JsonNode personsNode = root.path("persons");
        JsonNode fireStationsNode = root.path("firestations");
        JsonNode medicalRecordsNode = root.path("medicalrecords");

        // Read persons
        ObjectReader personsReader = mapper.readerFor(new TypeReference<List<Person>>() {});
        this.persons = personsReader.readValue(personsNode);

        // Read fire stations
        ObjectReader fireStationsReader = mapper.readerFor(new TypeReference<List<FireStation>>() {});
        this.fireStations = fireStationsReader.readValue(fireStationsNode);

        // Read medical records
        ObjectReader medicalRecordsReader = mapper.readerFor(new TypeReference<List<MedicalRecord>>() {});
        this.medicalRecords = medicalRecordsReader.readValue(medicalRecordsNode);
    }

    // -- Persons --

    // Create

    @Override
    public Person addPerson(Person person) {
        // Check if persons already exists
        if (this.persons.stream().anyMatch(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()))){
            // Person already exists, ignore add and return null
            // TODO : Add log
            return null;
        }

        // TODO : Add log
        this.persons.add(person);

        return person;
    }

    // Read

    @Override
    public List<Person> getPersons() {
        return this.persons;
    }

    @Override
    public Person getPerson(String firstName, String lastName) {
        return this.persons.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    // Update

    @Override
    public Person udpatePerson(Person person) {
        // TODO : implement update
        return person;
    }

    // Delete

    @Override
    public void deletePerson(String firstName, String lastName){
        // Get person to delete
        var toDelete = this.persons.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);

        if (toDelete == null) {
            // No person matched
            // TODO : Add log
            return;
        }

        // Remove the found person
        // TODO : Add log
        this.persons.remove(toDelete);
    }

    @Override
    public List<FireStation> getFireStations() {
        return this.fireStations;
    }

    @Override
    public FireStation getFireStation(String address, String station) {
        return this.fireStations.stream().filter(p -> p.getAddress().equals(address) && p.getStation().equals(station)).findFirst().orElse(null);
    }

    @Override
    public FireStation addFireStation(FireStation newFireStation) {
        // Check if fire station already exists
        if (this.fireStations.stream().anyMatch(f -> f.getAddress().equals(newFireStation.getAddress()) && f.getStation().equals(newFireStation.getStation()))){
            // Fire station already exists, ignore add and return null
            // TODO : Add log
            return null;
        }

        // TODO : Add log
        this.fireStations.add(newFireStation);

        return newFireStation;
    }

    @Override
    public FireStation udpateFireStation(FireStation fireStation) {
        // TODO : implement update
        return fireStation;
    }

    @Override
    public void deleteFireStation(String address, String station) {
        // Get fire station to delete
        var toDelete = this.fireStations.stream().filter(f -> f.getAddress().equals(address) && f.getStation().equals(station)).findFirst().orElse(null);

        if (toDelete == null) {
            // No fire station matched
            // TODO : Add log
            return;
        }

        // Remove the found fire station
        // TODO : Add log
        this.fireStations.remove(toDelete);
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        // Check if medical record already exists
        if (this.medicalRecords.stream().anyMatch(p -> p.getFirstName().equals(medicalRecord.getFirstName()) && p.getLastName().equals(medicalRecord.getLastName()))){
            // Medical record already exists, ignore add and return null
            // TODO : Add log
            return null;
        }

        // TODO : Add log
        this.medicalRecords.add(medicalRecord);

        return medicalRecord;
    }

    // Read

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return this.medicalRecords;
    }

    @Override
    public MedicalRecord getMedicalRecordsForPerson(String firstName, String lastName) {
        return this.medicalRecords.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    // Update

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        // TODO : implement update
        return medicalRecord;
    }

    // Delete

    @Override
    public void deleteMedicalRecord(String firstName, String lastName){
        // Get medical record to delete
        var toDelete = this.medicalRecords.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);

        if (toDelete == null) {
            // No medical record matched
            // TODO : Add log
            return;
        }

        // Remove the found medical record
        // TODO : Add log
        this.medicalRecords.remove(toDelete);
    }
}
