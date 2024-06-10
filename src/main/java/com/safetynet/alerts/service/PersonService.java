package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IDataRepository;
import com.safetynet.alerts.service.interfaces.IPersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    private final IDataRepository repository;

    public PersonService(IDataRepository repository) {
        this.repository = repository;
    }

    public List<Person> getPersons() {
        return this.repository.getPersons();
    }

    public Person getPerson(String firstName, String lastName, String email){
        return this.repository.getPerson(firstName, lastName, email);
    }

    public List<Person> getPersons(String firstName, String lastName) {
        return this.repository.getPersons().stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).toList();
    }

    public List<Person> getPersonsByAddresses(List<String> addresses) {
        var result = new ArrayList<Person>();

        for (Person person : this.repository.getPersons()) {
            if (addresses.contains(person.getAddress())) {
                result.add(person);
            }
        }

        return result;
    }

    public Person addPerson(Person newPerson) {
        return this.repository.addPerson(newPerson);
    }

    public Person updatePerson(Person person) {
        return this.repository.updatePerson(person);
    }


    public void deletePerson(String firstName, String lastName) {
        this.repository.deletePerson(firstName, lastName);
    }
}
