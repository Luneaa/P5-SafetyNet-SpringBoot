package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPersons() throws Exception {
        // Call "/persons" and get a list of persons with code 200

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "54bis Rue du Papier", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));

        when(personService.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/persons/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPersonsNoContent() throws Exception {
        // Call "/persons" and get no content code
        when (personService.getPersons()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/persons/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPerson() throws Exception {
        // Call "/persons/{firstName}/{lastName}" and get a person
        var person = new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com");
        when(personService.getPerson(any(String.class), any(String.class))).thenReturn(person);

        mockMvc.perform(get("/persons/{firstName}/{lastName}", "John", "Doe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addPerson() throws Exception {
        var newPerson = new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com");
        when(personService.addPerson(any(Person.class))).thenReturn(newPerson);

        mockMvc.perform(post("/persons/", newPerson)
                .content(objectMapper.writeValueAsString(newPerson))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson() throws Exception {
        Person updatePerson = new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com");
        when(personService.updatePerson(any(Person.class))).thenReturn(updatePerson);

        mockMvc.perform(put("/persons/", updatePerson)
                        .content(objectMapper.writeValueAsString(updatePerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(delete("/persons/{firstName}/{lastName}", "John", "Doe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
