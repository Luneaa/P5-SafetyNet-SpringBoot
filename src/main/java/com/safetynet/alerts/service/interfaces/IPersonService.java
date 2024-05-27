package com.safetynet.alerts.service.interfaces;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

// @Data
@Service
public interface IPersonService {
    List<Person> getPersons();

    Person getPerson(String firstName, String lastName);

    List<Person> getPersonsByAddresses(List<String> addresses);

    Person addPerson(Person person);

    Person updatePerson(Person person);

    void deletePerson(String firstName, String lastName);
}
