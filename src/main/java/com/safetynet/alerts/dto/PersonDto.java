package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.Person;

public class PersonDto {
    public String FirstName;

    public String LastName;

    public String Address;

    public String PhoneNumber;

    public PersonDto(Person person) {
        this.FirstName = person.getFirstName();
        this.LastName = person.getLastName();
        this.Address = person.getAddress();
        this.PhoneNumber = person.getPhone();
    }
}
