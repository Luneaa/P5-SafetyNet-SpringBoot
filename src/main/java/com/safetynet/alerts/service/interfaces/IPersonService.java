package com.safetynet.alerts.service.interfaces;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

// @Data
@Service
public interface IPersonService {
    public List<Person> getPersons();

    public Person getPerson(String firstName, String lastName);
}
