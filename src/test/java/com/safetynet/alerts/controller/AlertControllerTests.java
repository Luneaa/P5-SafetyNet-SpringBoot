package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlertController.class)
class AlertControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private FireStationService fireStationService;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    void getPersonsFromFireStation() throws Exception {
        // Mock fire station by station number
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "1"));

        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(fireStations);

        // Mock get persons by addresses
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersonsByAddresses(any())).thenReturn(persons);

        // Mock get medical record
        var johnDoeMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        var michelVitesseRecord = new MedicalRecord("Michelle", "Vitesse", "02/03/2010", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" });
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(johnDoeMedicalRecord);
        when(medicalRecordService.getMedicalRecord("Michel", "Vitesse")).thenReturn(michelVitesseRecord);

        mockMvc.perform(get("/fireStation").param("stationNumber", "1")).andExpect(status().isOk());
    }

    @Test
    void getPersonsFromFireStationNoContent() throws Exception {
        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/fireStation").param("stationNumber", "1")).andExpect(status().isNoContent());
    }

    @Test
    void getChildAlert() throws Exception {
        // Mock get persons by addresses
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "12 Rue du Persil Heureux", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersonsByAddresses(any())).thenReturn(persons);

        // Mock get medical record
        var johnDoeMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        var michelVitesseRecord = new MedicalRecord("Michelle", "Vitesse", "02/03/2010", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" });
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(johnDoeMedicalRecord);
        when(medicalRecordService.getMedicalRecord("Michel", "Vitesse")).thenReturn(michelVitesseRecord);

        mockMvc.perform(get("/childAlert").param("address", "12 Rue du Persil Heureux")).andExpect(status().isOk());
    }

    @Test
    void getChildAlertNoContent() throws Exception {
        when(personService.getPersonsByAddresses(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/childAlert").param("address", "12 Rue du Persil Heureux")).andExpect(status().isNoContent());
    }

    @Test
    void phoneAlert() throws Exception {
        // Mock fire station by station number
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "1"));

        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(fireStations);

        // Mock get persons by addresses
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));
        persons.add(new Person("Didier", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "didiervitesse@gmail.com"));

        when(personService.getPersonsByAddresses(any())).thenReturn(persons);

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk());
    }

    @Test
    void phoneAlertNoContent() throws Exception {
        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isNoContent());
    }

    @Test
    void fire() throws Exception {
        // Mock get persons by addresses
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersonsByAddresses(any())).thenReturn(persons);

        // Mock get fire station by address
        var firestation = new FireStation("12 Rue du Persil Heureux", "1");
        when(fireStationService.getFireStation(any())).thenReturn(firestation);

        // Mock get medical record
        var johnDoeMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        var michelVitesseRecord = new MedicalRecord("Michelle", "Vitesse", "02/03/2010", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" });
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(johnDoeMedicalRecord);
        when(medicalRecordService.getMedicalRecord("Michel", "Vitesse")).thenReturn(michelVitesseRecord);

        mockMvc.perform(get("/fire").param("address", "12 Rue du Persil Heureux")).andExpect(status().isOk());
    }

    @Test
    void stations() throws Exception {
        // Mock fire station by station number
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("12 Rue du Persil Heureux", "1"));
        fireStations.add(new FireStation("25 Avenue du Chemin Perdu", "2"));

        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(fireStations);

        // Mock get persons by addresses
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersonsByAddresses(any())).thenReturn(persons);

        // Mock get medical record
        var johnDoeMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        var michelVitesseRecord = new MedicalRecord("Michelle", "Vitesse", "02/03/2010", new String[] { "cyanide:500mg" }, new String[] { "dust", "cats" });
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(johnDoeMedicalRecord);
        when(medicalRecordService.getMedicalRecord("Michel", "Vitesse")).thenReturn(michelVitesseRecord);

        mockMvc.perform(get("/stations").param("station_numbers", "1, 2")).andExpect(status().isOk());
    }

    @Test
    void stationsNoContent() throws Exception {
        when(fireStationService.getFireStationsByStationNumber(any(String.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/stations").param("station_numbers", "1, 2")).andExpect(status().isNoContent());
    }

    @Test
    void personInfo() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        when(personService.getPersons("John", "Doe")).thenReturn(persons);

        // Mock get medical record
        var johnDoeMedicalRecord = new MedicalRecord("John", "Doe", "12/12/1912", new String[] { "paracetamol:350mg" }, new String[] { "peanut" });
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(johnDoeMedicalRecord);

        mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Doe")).andExpect(status().isOk());
    }

    @Test
    void personInfoNoContent() throws Exception {
        when(personService.getPersons("John", "Doe")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Doe")).andExpect(status().isNoContent());
    }

    @Test
    void communityEmail() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "25 Avenue du Chemin Perdu", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/communityEmail").param("city", "Pau")).andExpect(status().isOk());
    }

    @Test
    void communityEmailNoContent() throws Exception {
        when(personService.getPersons("John", "Doe")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/communityEmail").param("city", "Pau")).andExpect(status().isNoContent());
    }
}
