package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IDataRepository;
import com.safetynet.alerts.service.interfaces.IPersonService;
import org.springframework.stereotype.Service;

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

    public Person getPerson(String firstName, String lastName){
        return this.repository.getPerson(firstName, lastName);
    }
}
