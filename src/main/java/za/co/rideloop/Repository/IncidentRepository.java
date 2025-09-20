package za.co.rideloop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.rideloop.Domain.Incident;
import za.co.rideloop.Domain.Rental;

import java.util.List;

/**
 * IncidentRepository.java
 * IncidentRepository model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
    List<Incident> findByRentals(Rental rental);
}
