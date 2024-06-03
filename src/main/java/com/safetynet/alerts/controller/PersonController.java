package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.interfaces.IPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final IPersonService personService;

    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons/")
    public ResponseEntity<List<Person>> getPersons() {
        var persons = personService.getPersons();

        if (persons.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/persons/{firstName}/{lastName}/{email}")
    public ResponseEntity<Person> getPerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("email") String email){
        var person = personService.getPerson(firstName, lastName, email);

        if (person == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/persons/")
    public ResponseEntity<Person> postPerson(@RequestBody Person person){
        var newPerson = personService.addPerson(person);

        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    @PutMapping("/persons/")
    public ResponseEntity<Person> putPerson(@RequestBody Person person){
        var updatedPerson = personService.updatePerson(person);

        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/persons/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable ("firstName") String firstName, @PathVariable("lastName") String lastName){
        personService.deletePerson(firstName, lastName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
