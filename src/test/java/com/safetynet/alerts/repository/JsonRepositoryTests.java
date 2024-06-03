package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonRepositoryTests {

    private static JsonRepository jsonRepository;

    @BeforeAll
    public static void setUp() throws IOException { jsonRepository = new JsonRepository(); }

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

        var updatedPerson = jsonRepository.udpatePerson(personToUpdate);

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
        var updatedPerson = jsonRepository.udpatePerson(personToUpdate);

        assertNull(updatedPerson);
    }

    @Test
    void updatePersonNotFoundFirstNameMatch() {
        var personToUpdate = new Person("John", "Vitesse","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "didiervitesse@email.com");
        var updatedPerson = jsonRepository.udpatePerson(personToUpdate);

        assertNull(updatedPerson);
    }

    @Test
    void updatePersonNotFoundLastNameMatch() {
        var personToUpdate = new Person("Didier", "Boyd","12 Rue du Persil Heureux", "Pau", "1234", "+33456787512", "didiervitesse@email.com");
        var updatedPerson = jsonRepository.udpatePerson(personToUpdate);

        assertNull(updatedPerson);
    }
}
