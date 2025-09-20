package za.co.rideloop.Service;

import org.springframework.stereotype.Service;
import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Repository.IncidentRepository;
import za.co.rideloop.Factory.IncidentFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * IncidentService.java
 * IncidentService model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */
@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // Create a new incident linked to one or more rentals
    public Incident createIncident(String type, String description, List<Rental> rentals) {
        if (rentals == null || rentals.isEmpty()) {
            throw new IllegalArgumentException("Incident must be linked to at least one Rental");
        }

        // Allow only three types
        if (!"Security".equalsIgnoreCase(type)
                && !"Maintenance".equalsIgnoreCase(type)
                && !"Accident".equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("Incident type must be 'Security', 'Maintenance' or 'Accident'");
        }

        Incident incident = IncidentFactory.createIncident(type, description, rentals);
        return incidentRepository.save(incident);
    }

    // Get all incidents
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    // Get incidents by a single rental
    public List<Incident> getIncidentsByRental(Rental rental) {
        if (rental == null) return new ArrayList<>();
        return incidentRepository.findByRentals(rental);
    }

    // Get incidents by multiple rentals
    public List<Incident> getIncidentsByRentals(List<Rental> rentals) {
        if (rentals == null || rentals.isEmpty()) return new ArrayList<>();

        Set<Incident> incidents = new HashSet<>();
        for (Rental rental : rentals) {
            incidents.addAll(incidentRepository.findByRentals(rental));
        }
        return new ArrayList<>(incidents); // removes duplicates
    }

    // Delete an incident by its ID
    public void deleteIncidentById(int incidentID) {
        incidentRepository.deleteById(incidentID);
    }
}

