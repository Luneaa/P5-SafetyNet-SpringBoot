package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.interfaces.IFireStationService;
import com.safetynet.alerts.service.interfaces.IMedicalRecordService;
import com.safetynet.alerts.service.interfaces.IPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class AlertController {

    private final IFireStationService fireStationService;

    private final IPersonService personService;

    private final IMedicalRecordService medicalRecordService;

    private final DateTimeFormatter formatter;

    public AlertController(IFireStationService fireStationService, IPersonService personService, IMedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }

    @GetMapping("/fireStation")
    public ResponseEntity<PersonGroupDto> getPersonsFromFireStation(@RequestParam String stationNumber) {
        // Get fire stations using the station number
        var fireStations = fireStationService.getFireStationsByStationNumber(stationNumber);

        // Get the list of addresses
        List<String> addresses = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            addresses.add(fireStation.getAddress());
        }

        // Get the persons using the station addresses
        var persons = personService.getPersonsByAddresses(addresses);

        if (persons.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Create DTO response
        // Count adults and children
        var adultCount = 0;
        var childCount = 0;

        List<PersonDto> personDtos = new ArrayList<>();
        for (Person person : persons){
            personDtos.add(new PersonDto(person));

            // Get medical record for birthdate and age
            var medicalRecord = this.medicalRecordService.getMedicalRecord(person.getFirstName(), person.getLastName());
            var age = getAge(medicalRecord.getBirthdate());

            // Adult are 19+ years old
            if (age > 18) {
                adultCount++;
            }
            else{
                childCount++;
            }
        }


        var result = new PersonGroupDto(personDtos, adultCount, childCount);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDto> getChildAlert(@RequestParam String address) {
        // Get persons at given address
        var personsAtAddress = this.personService.getPersonsByAddresses(Collections.singletonList(address));

        // Check age
        var childrenDto = new ArrayList<ChildAlertChildDto>();
        for (Person person : personsAtAddress) {
            var medicalRecord = this.medicalRecordService.getMedicalRecord(person.getFirstName(), person.getLastName());
            var age = getAge(medicalRecord.getBirthdate());
            // Children are 18-
            if (age <= 18) {
                var familyMembers = personsAtAddress.stream().filter(p -> !p.getFirstName().equals(person.getFirstName()) || !p.getLastName().equals(person.getLastName())).toList();
                var familyMembersDto = new ArrayList<ChildAlertFamilyMemberDto>();
                for (Person familyMember : familyMembers) {
                    familyMembersDto.add(new ChildAlertFamilyMemberDto(familyMember.getFirstName(), familyMember.getLastName()));
                }
                childrenDto.add(new ChildAlertChildDto(person.getFirstName(), person.getLastName(), age, familyMembersDto));
            }
        }

        if (childrenDto.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(new ChildAlertDto(childrenDto), HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> phoneAlert(@RequestParam String firestation) {
        var fireStations = this.fireStationService.getFireStationsByStationNumber(firestation);
        var persons = this.personService.getPersonsByAddresses(fireStations.stream().map(f -> f.getAddress()).toList());
        List<String> result = new ArrayList<>();

        for (Person person : persons) {
            if (!result.contains(person.getPhone())){
                result.add(person.getPhone());
            }
        }

        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<FireStationPersonListDto> fire(@RequestParam String address) {
        var persons = this.personService.getPersonsByAddresses(Collections.singletonList(address));
        var fireStation = this.fireStationService.getFireStation(address);
        List<PersonFireStationDto> personList = new ArrayList<>();

        for (Person person : persons) {
            var medicalRecord = this.medicalRecordService.getMedicalRecord(person.getFirstName(), person.getLastName());
            personList.add(new PersonFireStationDto(person.getFirstName(), person.getLastName(), person.getPhone(), getAge(medicalRecord.getBirthdate()), medicalRecord.getMedications(), medicalRecord.getAllergies()));
        }

        return new ResponseEntity<>(new FireStationPersonListDto(fireStation.getStation() ,personList), HttpStatus.OK);
    }

    // TODO : /stations?stations=<a list of station_numbers>

    @GetMapping("/personInfo")
    public ResponseEntity<PersonInfoDto> personInfo(@RequestParam String firstName, @RequestParam String lastName){
        var person = this.personService.getPerson(firstName, lastName);
        var medicalRecord = this.medicalRecordService.getMedicalRecord(firstName, lastName);

        var result = new PersonInfoDto(person.getFirstName(),
                                       person.getLastName(),
                                       person.getPhone(),
                                       getAge(medicalRecord.getBirthdate()),
                                       medicalRecord.getMedications(),
                                       medicalRecord.getAllergies());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> communityEmail(@RequestParam String city) {
        var persons = this.personService.getPersons();
        List<String> result = new ArrayList<>();

        for (Person person : persons) {
            if (person.getCity().equals(city)){
                result.add(person.getEmail());
            }
        }

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private int getAge(String birthdate) {
        var parsedDate = LocalDate.parse(birthdate, formatter);
        return (int) ChronoUnit.YEARS.between(parsedDate, LocalDate.now());
    }
}
