package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonServiceTests {

    private static PersonService personService;

    private IDataRepository dataRepository;

    @BeforeEach
    public void setUp()
    {
        dataRepository = Mockito.mock(IDataRepository.class);
        personService = new PersonService(dataRepository);
    }

    @Test
    void getPersons() {
        var persons = new ArrayList<Person>();
        persons.add(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "54bis Rue du Papier", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));
        when(dataRepository.getPersons()).thenReturn(persons);

        assertEquals(2, personService.getPersons().size());
    }

    @Test
    void getPerson() {
        var johnDoe = new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com");
        when(dataRepository.getPerson(any(String.class), any(String.class), any(String.class))).thenReturn(johnDoe);

        var person = personService.getPerson("John", "Doe", "johndoe@gmail.com");

        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("2 Rue des Sapins", person.getAddress());
        assertEquals("Pau", person.getCity());
        assertEquals("1234", person.getZip());
        assertEquals("+33456787512", person.getPhone());
        assertEquals("johndoe@gmail.com", person.getEmail());
    }

    @Test
    void getPersonsWithoutEmail() {
        var persons = new ArrayList<Person>();
        persons.add(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("John", "Doe", "54bis Rue du Papier", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));
        persons.add(new Person("Didier", "Vitesse", "54bis Rue du Papier", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));
        when(dataRepository.getPersons()).thenReturn(persons);

        assertEquals(2, personService.getPersons("John", "Doe").size());
    }

    @Test
    void getPersonsByAddresses() {
        var persons = new ArrayList<Person>();
        persons.add(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));
        persons.add(new Person("Michel", "Vitesse", "54bis Rue du Papier", "Grenoble", "38000", "+3345678765465", "michelvitesse@gmail.com"));
        when(dataRepository.getPersons()).thenReturn(persons);

        var addresses = new ArrayList<String>();
        addresses.add("2 Rue des Sapins");

        var result = personService.getPersonsByAddresses(addresses);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstName());
        assertEquals("Doe", result.getFirst().getLastName());
    }

    @Test
    void addPerson() {
        when(dataRepository.addPerson(any(Person.class))).thenReturn(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        var result = personService.addPerson(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("2 Rue des Sapins", result.getAddress());
        assertEquals("Pau", result.getCity());
        assertEquals("1234", result.getZip());
        assertEquals("+33456787512", result.getPhone());
        assertEquals("johndoe@gmail.com", result.getEmail());
    }

    @Test
    void updatePerson() {
        when(dataRepository.updatePerson(any(Person.class))).thenReturn(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        var result = personService.updatePerson(new Person("John", "Doe", "2 Rue des Sapins", "Pau", "1234", "+33456787512", "johndoe@gmail.com"));

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("2 Rue des Sapins", result.getAddress());
        assertEquals("Pau", result.getCity());
        assertEquals("1234", result.getZip());
        assertEquals("+33456787512", result.getPhone());
        assertEquals("johndoe@gmail.com", result.getEmail());
    }

    @Test
    void deletePerson() {
        personService.deletePerson("John", "Doe");
    }
}
