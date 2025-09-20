package za.co.rideloop.FactoryTest;

import org.junit.jupiter.api.Test;
import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Factory.IncidentFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Incident.java
 * Incident model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */

public class IncidentFactoryTest {
    @Test
    void testCreateIncidentSuccess() {
        // Prepare rentals
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental()); // simple empty rental for test

        Incident incident = IncidentFactory.createIncident(
                "Maintenance",
                "Car engine check",
                rentals
        );

        assertNotNull(incident);
        assertEquals("Maintenance", incident.getType());
        assertEquals("Car engine check", incident.getDescription());
        assertNotNull(incident.getDateReported());
        assertEquals(1, incident.getRentals().size());

        System.out.println("Incident created successfully"+incident);
    }

    @Test
    void testCreateIncidentWithEmptyType_shouldThrowException() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            IncidentFactory.createIncident(
                    "",
                    "Some description",
                    rentals
            );
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("type"));
        System.err.println(actualMessage);
    }

    @Test
    void testCreateIncidentWithNullRentals_shouldDefaultToEmptyList() {
        Incident incident = IncidentFactory.createIncident(
                "Accident",
                "Minor scratch on car",
                null // null rentals
        );

        assertNotNull(incident);
        assertNotNull(incident.getRentals());
        assertEquals(0, incident.getRentals().size());

        System.out.println(incident);
    }

    @Test
    void testDateReportedIsCurrentDate() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental());

        LocalDate beforeCreation = LocalDate.now();
        Incident incident = IncidentFactory.createIncident(
                "Security",
                "Unauthorized entry",
                rentals
        );
        LocalDate afterCreation = LocalDate.now();

        assertNotNull(incident.getDateReported());
        // Ensure dateReported is today
        assertFalse(incident.getDateReported().isBefore(beforeCreation));
        assertFalse(incident.getDateReported().isAfter(afterCreation));

        System.out.println("Incident dateReported: " + incident.getDateReported());
    }
}
