package za.co.rideloop.ServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import za.co.rideloop.Domain.Car;
import za.co.rideloop.Domain.CustomerProfile;
import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Repository.CarRepository;
import za.co.rideloop.Repository.CustomerProfileRepository;
import za.co.rideloop.Repository.RentalRepository;
import za.co.rideloop.Service.IncidentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * IncidentServiceTest.java
 * IncidentServiceTest model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */
@SpringBootTest
@Transactional
public class IncidentServiceTest {
    @Autowired
    private IncidentService incidentService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private RentalRepository rentalRepository;

    private Car sampleCar;
    private CustomerProfile sampleCustomer;
    private Rental sampleRental;

    @BeforeEach
    void setUp() {
        // Persist Car
        sampleCar = za.co.rideloop.Factory.CarFactory.createCar(
                0, "Toyota", "Corolla", 2020, "ABC123", 350.0,
                "Available", "Sedan", 50000, "2025-01-01", "2025-06-01", null
        );
        sampleCar = carRepository.save(sampleCar);

        // Persist CustomerProfile
        sampleCustomer = za.co.rideloop.Factory.CustomerProfileFactory.createFullCustomerProfile(
                null, "John", "Doe", "9900112233", "B1234567", "0812345678", null, "pending"
        );
        sampleCustomer = customerProfileRepository.save(sampleCustomer);

        // Persist Rental
        sampleRental = za.co.rideloop.Factory.RentalFactory.createRental(
                sampleCar, sampleCustomer,
                LocalDate.of(2025, 9, 18),
                LocalDate.of(2025, 9, 20),
                "City Center", "Airport", 101, 700.0, "Booked"
        );
        sampleRental = rentalRepository.save(sampleRental);
    }

    // ===== CREATE INCIDENT =====
    @Test
    @Commit
    void createIncidentTest() {
        Incident incident = incidentService.createIncident(
                "Accident", "Minor fender bender", Arrays.asList(sampleRental)
        );

        assertNotNull(incident);
        assertTrue(incident.getIncidentID() > 0);
        assertEquals("Accident", incident.getType());
        System.out.println("Created Incident: " + incident);
    }

    // ===== GET INCIDENTS BY SINGLE RENTAL =====
    @Test
    void getIncidentsByRentalTest() {
        Incident incident = incidentService.createIncident(
                "Maintenance", "Oil change required", Arrays.asList(sampleRental)
        );

        List<Incident> incidents = incidentService.getIncidentsByRental(sampleRental);
        assertFalse(incidents.isEmpty());
        assertEquals(1, incidents.size());
        assertEquals("Maintenance", incidents.get(0).getType());
        System.out.println("Incidents for Rental: " + incidents);
        System.out.println("Incident ID: " + incident.getIncidentID() +
                ", type: " + incident.getType() +
                ", rentals count: " + incident.getRentals().size());
    }

    // ===== GET ALL INCIDENTS =====
    @Test
    void getAllIncidentsTest() {
        incidentService.createIncident("Accident", "Minor fender bender", Arrays.asList(sampleRental));
        incidentService.createIncident("Security", "Alarm triggered", Arrays.asList(sampleRental));

        List<Incident> allIncidents = incidentService.getAllIncidents();
        assertEquals(2, allIncidents.size());
        System.out.println("All Incidents: " + allIncidents);
    }

    // ===== DELETE INCIDENT =====
    @Test
    void deleteIncidentTest() {
        Incident incident = incidentService.createIncident("Accident", "Minor fender bender", Arrays.asList(sampleRental));
        int id = incident.getIncidentID();

        incidentService.deleteIncidentById(id);
        List<Incident> all = incidentService.getAllIncidents();
        assertTrue(all.isEmpty());
        System.out.println("Deleted Incident with ID: " + id);
    }
}
