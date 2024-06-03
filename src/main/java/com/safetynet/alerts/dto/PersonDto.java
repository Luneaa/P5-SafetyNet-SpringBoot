package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.Person;
import lombok.Getter;

@Getter
public class PersonDto {

    private final String firstName;

    private final String lastName;

    private final String address;

    private final String phoneNumber;

    public PersonDto(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phoneNumber = person.getPhone();
    }

}
