package com.safetynet.alerts.integration;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AlertsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void FireStationIT() {
        var fireStations = this.restTemplate.getForObject("http://localhost:" + port + "/fireStation?stationNumber=1", PersonGroupDto.class);

        assertEquals(5, fireStations.getAdultCount());
        assertEquals(1, fireStations.getChildCount());
        assertEquals(6, fireStations.getPersons().size());
    }

    @Test
    void ChildAlertIT() {
        var childAlert = this.restTemplate.getForObject("http://localhost:" + port + "/childAlert?address=1509 Culver St", ChildAlertDto.class);

        assertEquals(2, childAlert.getChildren().size());
        assertEquals("Tenley", childAlert.getChildren().getFirst().getFirstName());
        assertEquals("Boyd", childAlert.getChildren().getFirst().getLastName());
        assertEquals(12, childAlert.getChildren().getFirst().getAge());
        assertEquals(3, childAlert.getFamilyMembers().size());
    }

    @Test
    void phoneAlertIT() {
        var phoneAlert = this.restTemplate.exchange("http://localhost:" + port + "/phoneAlert?firestation=1", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}).getBody();

        assertNotNull(phoneAlert);
        assertEquals(4, phoneAlert.size());
        assertEquals("841-874-6512", phoneAlert.getFirst());
        assertEquals("841-874-8547", phoneAlert.get(1));
        assertEquals("841-874-7462", phoneAlert.get(2));
        assertEquals("841-874-7784", phoneAlert.get(3));
    }

    @Test
    void fireIT(){
        var fire = this.restTemplate.getForObject("http://localhost:" + port + "/fire?address=748 Townings Dr", FireStationPersonListDto.class);

        assertEquals("3", fire.getNumber());
        assertEquals(2, fire.getPersons().size());
        assertTrue(fire.getPersons().stream().anyMatch(p -> p.getFirstName().equals("Foster") && p.getLastName().equals("Shepard")));
    }

    @Test
    void stations() {
        var stations = this.restTemplate.exchange("http://localhost:" + port + "/stations?station_numbers=1, 2", HttpMethod.GET, null, new ParameterizedTypeReference<List<StationAlertDto>>() {}).getBody();

        assertNotNull(stations);
        assertEquals(6, stations.size());

        // Get data for address 644 Gershwin Cir
        var station = stations.stream().filter(s -> s.getAddress().equals("644 Gershwin Cir")).findFirst();

        assertTrue(station.isPresent());
        var persons = station.get().getPersons();
        assertEquals(1, persons.size());
        assertEquals("Peter", persons.getFirst().getFirstName());
        assertEquals("Duncan", persons.getFirst().getLastName());
        assertEquals("841-874-6512", persons.getFirst().getPhoneNumber());
        assertEquals(23, persons.getFirst().getAge());
        assertEquals(0, persons.getFirst().getMedications().length);
        assertEquals("shellfish", persons.getFirst().getAllergies()[0]);
    }

    @Test
    void personInfo() {
        var personInfo = this.restTemplate.exchange("http://localhost:" + port + "/personInfo?firstName=Eric&lastName=Cadigan", HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonInfoDto>>() {}).getBody();

        assertNotNull(personInfo);
        assertEquals(1, personInfo.size());
        var person = personInfo.getFirst();
        assertEquals("Eric", person.getFirstName());
        assertEquals("Cadigan", person.getLastName());
        assertEquals("841-874-7458", person.getPhoneNumber());
        assertEquals(78, person.getAge());
        assertEquals(1, person.getMedications().length);
        assertEquals("tradoxidine:400mg", person.getMedications()[0]);
        assertEquals(0, person.getAllergies().length);
    }

    @Test
    void communityEmail() {
        var emails = this.restTemplate.exchange("http://localhost:" + port + "/communityEmail?city=Culver", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}).getBody();

        assertNotNull(emails);
        assertEquals(15, emails.size());
        assertTrue(emails.contains("jaboyd@email.com"));
        assertTrue(emails.contains("drk@email.com"));
        assertTrue(emails.contains("tenz@email.com"));
        assertTrue(emails.contains("aly@imail.com"));
    }
}
