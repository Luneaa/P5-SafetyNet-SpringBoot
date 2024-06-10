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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JsonRepository implements IDataRepository {

    private final List<Person> persons;

    private final List<FireStation> fireStations;

    private final List<MedicalRecord> medicalRecords;

    private static final Logger logger = LogManager.getLogger(JsonRepository.class);

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
            logger.error("Person already exists addPerson stopped");
            return null;
        }

        this.persons.add(person);
        logger.info("Person added to the repository");

        return person;
    }

    // Read

    @Override
    public List<Person> getPersons() {
        return this.persons;
    }

    @Override
    public Person getPerson(String firstName, String lastName, String email) {
        return this.persons.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName) && p.getEmail().equals(email)).findFirst().orElse(null);
    }

    // Update

    @Override
    public Person updatePerson(Person person) {
        Person personInList = this.persons.stream().filter(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())).findFirst().orElse(null);

        if (personInList == null){
            // No person found to update
            logger.error("Person to update not found updatePerson stopped");
            return null;
        }

        personInList.setAddress(person.getAddress());
        personInList.setCity(person.getCity());
        personInList.setZip(person.getZip());
        personInList.setPhone(person.getPhone());
        personInList.setEmail(person.getEmail());

        logger.info("Person updated in the repository");

        return personInList;
    }

    // Delete

    @Override
    public void deletePerson(String firstName, String lastName){
        // Get person to delete
        var toDelete = this.persons.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);

        if (toDelete == null) {
            // No person matched
            logger.error("Person to delete not found in the repository");
            return;
        }

        // Remove the found person

        this.persons.remove(toDelete);
        logger.info("Person deleted in the repository");
    }

    @Override
    public List<FireStation> getFireStations() {
        return this.fireStations;
    }

    @Override
    public FireStation getFireStation(String address) {
        return this.fireStations.stream().filter(f -> f.getAddress().equals(address)).findFirst().orElse(null);
    }

    @Override
    public List<FireStation> getFireStationsByStationNumber(String stationNumber) {
        return this.fireStations.stream().filter(f -> f.getStation().equals(stationNumber)).toList();
    }

    @Override
    public FireStation addFireStation(FireStation newFireStation) {
        // Check if fire station already exists
        if (this.fireStations.stream().anyMatch(f -> f.getAddress().equals(newFireStation.getAddress()))){
            // Fire station already exists, ignore add and return null
            logger.error("Fire station to add already found in repository, stopped addFireStation");
            return null;
        }

        this.fireStations.add(newFireStation);
        logger.info("Fire station added to the repository");

        return newFireStation;
    }

    @Override
    public FireStation updateFireStation(FireStation fireStation) {
        FireStation fireStationInList = this.fireStations.stream().filter(p -> p.getAddress().equals(fireStation.getAddress())).findFirst().orElse(null);

        if (fireStationInList == null){
            // No fire station to update found
            logger.error("No fire station to update found in repository, updateFireStation stopped");
            return null;
        }

        fireStationInList.setStation(fireStation.getStation());
        logger.info("Fire station updated in repository");

        return fireStationInList;
    }

    @Override
    public void deleteFireStation(String address, String station) {
        // Get fire station to delete
        var toDelete = this.fireStations.stream().filter(f -> f.getAddress().equals(address) && f.getStation().equals(station)).findFirst().orElse(null);

        if (toDelete == null) {
            // No fire station matched
            logger.error("Fire station to delete not found in repository, deleteFireStation stopped");
            return;
        }

        // Remove the found fire station
        this.fireStations.remove(toDelete);
        logger.info("Fire station removed from repository");
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        // Check if medical record already exists
        if (this.medicalRecords.stream().anyMatch(p -> p.getFirstName().equals(medicalRecord.getFirstName()) && p.getLastName().equals(medicalRecord.getLastName()))){
            // Medical record already exists, ignore add and return null
            logger.error("Medical record already exists in repository, stopped addMedicalRecord");
            return null;
        }

        this.medicalRecords.add(medicalRecord);
        logger.info("Medical record added to the repository");

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
        MedicalRecord medicalRecordInList = this.medicalRecords.stream().filter(p -> p.getFirstName().equals(medicalRecord.getFirstName()) && p.getLastName().equals(medicalRecord.getLastName())).findFirst().orElse(null);

        if (medicalRecordInList == null) {
            // No medical record found to update
            logger.error("Medical record to update not found in repository, stopped updateMedicalRecord");
            return null;
        }

        medicalRecordInList.setBirthdate(medicalRecord.getBirthdate());
        medicalRecordInList.setMedications(medicalRecord.getMedications());
        medicalRecordInList.setAllergies(medicalRecord.getAllergies());

        logger.info("Updated medical record in repository");

        return medicalRecordInList;
    }

    // Delete

    @Override
    public void deleteMedicalRecord(String firstName, String lastName){
        // Get medical record to delete
        var toDelete = this.medicalRecords.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().orElse(null);

        if (toDelete == null) {
            // No medical record matched
            logger.error("Medical record to delete not found, stopped deleteMedicalRecord");
            return;
        }

        // Remove the found medical record
        this.medicalRecords.remove(toDelete);
        logger.info("Medical record deleted from repository");
    }
}
