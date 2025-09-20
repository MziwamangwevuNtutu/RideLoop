package za.co.rideloop.Factory;

import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * IncidentFactory.java
 * IncidentFactory model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */

public class IncidentFactory {

    public static Incident createIncident(String type, String description, List<Rental> rentals) {

        // Ensure rentals is never null
        List<Rental> safeRentals = (rentals != null) ? rentals : new ArrayList<>();

        return new Incident.Builder()
                .type(type)
                .description(description)
                .dateReported(LocalDate.now()) // always current date
                .rentals(safeRentals)          // Many-to-Many
                .build();
    }
}
