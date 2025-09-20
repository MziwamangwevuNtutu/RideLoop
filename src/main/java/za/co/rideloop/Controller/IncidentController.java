package za.co.rideloop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Repository.RentalRepository;
import za.co.rideloop.Service.IncidentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IncidentController.java
 * Handles HTTP requests for managing incidents.
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */
@RestController
@RequestMapping("/incidents")
@CrossOrigin(origins = "http://localhost:3000")
public class IncidentController {

    private final IncidentService incidentService;
    private final RentalRepository rentalRepository;

    @Autowired
    public IncidentController(IncidentService incidentService, RentalRepository rentalRepository) {
        this.incidentService = incidentService;
        this.rentalRepository = rentalRepository;
    }

    // Create a new incident linked to a rental
    @PostMapping("/create")
    public ResponseEntity<?> createIncident(
            @RequestParam String type,
            @RequestParam String description,
            @RequestParam int rentalId
    ) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isEmpty()) {
            return ResponseEntity.badRequest().body("Rental not found");
        }

        try {
            Rental rental = optionalRental.get();
            List<Rental> rentals = new ArrayList<>();
            rentals.add(rental);

            Incident incident = incidentService.createIncident(type, description, rentals);
            return ResponseEntity.ok(incident);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }

    // Get all incidents
    @GetMapping("incidents/getAll")
    public ResponseEntity<List<Incident>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    // Get incidents by rental ID
    @GetMapping("/rental/{rentalId}")
    public ResponseEntity<?> getIncidentsByRental(@PathVariable int rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isEmpty()) {
            return ResponseEntity.badRequest().body("Rental not found");
        }

        List<Incident> incidents = incidentService.getIncidentsByRental(optionalRental.get());
        return ResponseEntity.ok(incidents);
    }

    // Delete incident by ID
    @DeleteMapping("/{incidentId}")
    public ResponseEntity<?> deleteIncident(@PathVariable int incidentId) {
        try {
            incidentService.deleteIncidentById(incidentId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
