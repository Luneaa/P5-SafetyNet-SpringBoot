package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface IDataRepository {

    Person addPerson(Person person);

    List<Person> getPersons();

    Person getPerson(String firstName, String lastName);

    Person udpatePerson(Person person);

    void deletePerson(String firstName, String lastName);
}
