package za.co.rideloop.Repository;
/**
 * RentalRepository.java
 * RentalRepository Model Class
 *
 * @Author: Mziwamangwevu Ntutu
 * @Student Number: 217054420
 **/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.rideloop.Domain.Rental;

import java.util.List;

@Repository
public interface RentalRepository  extends JpaRepository<Rental,Integer > {



}
