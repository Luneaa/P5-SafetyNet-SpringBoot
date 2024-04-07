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
}
