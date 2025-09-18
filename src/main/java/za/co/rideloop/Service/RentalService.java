package za.co.rideloop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.rideloop.Domain.Invoice;
import za.co.rideloop.Domain.Payment;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Repository.RentalRepository;
import za.co.rideloop.Util.Helper;

import java.util.List;
import java.util.Optional;

/**
 * RentalFactory.java
 * RentalFactory Model Class
 *
 * @Author: Mziwamangwevu Ntutu
 * @Student Number: 217054420
 * Group 3 I
 **/

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // ===== Create =====
    public Rental create(Rental rental) {
        return rentalRepository.save(rental);
    }

    // ===== Read =====
    public Optional<Rental> read(Integer id) {
        return rentalRepository.findById(id);
    }

    // ===== Update =====
    public Rental update(Integer rentalId, Rental rentalDetails) {
        // 1. Find the existing rental by its ID.
        Optional<Rental> existingRentalOptional = rentalRepository.findById(rentalId);

        // 2. If an existing rental is found, update its fields using the builder.
        if (existingRentalOptional.isPresent()) {
            Rental existingRental = existingRentalOptional.get();

            // 3. Use the builder to copy the existing rental and apply the new details.
            Rental updatedRental = new Rental.RentalBuilder()
                    .RentalBuilderCopy(existingRental)
                    .setCar(rentalDetails.getCar())
                    .setCustomerProfile(rentalDetails.getCustomerProfile())
                    .setStartDate(rentalDetails.getStartDate())
                    .setEndDate(rentalDetails.getEndDate())
                    .setPickupLocation(rentalDetails.getPickupLocation())
                    .setDropoffLocation(rentalDetails.getDropoffLocation())
                    .setInsuranceID(rentalDetails.getInsuranceID())
                    .setTotalCost(rentalDetails.getTotalCost())
                    .setStatus(rentalDetails.getStatus())
                    .build();

            // 4. Save the new, updated rental object.
            return rentalRepository.save(updatedRental);
        }

        // 5. If no existing rental is found, return null.
        return null;
    }



    // ===== Get All =====
    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    // ===== Delete =====
    public void delete(int id) {
        rentalRepository.deleteById(id);
    }
}
//public class RentalService {
//
//
//    @Autowired
//    private RentalRepository repository;
//
//    // ===== Create =====
//    public Rental create(Rental rental) {
//
//        return this.repository.save(rental);
//    }
//    // ===== Read =====
//
//    public Rental read(Integer id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    // ===== Update =====
//
//
//    public Rental update(Rental rental) {
//        // 1. Check if the input rental object is null or has no ID.
//        if (rental == null || rental.getRentalID() == 0) {
//            return null;
//        }
//
//        // 2. Find the existing rental by its ID.
//        Rental existingRental = repository.findById(rental.getRentalID()).orElse(null);
//
//        // 3. If an existing rental is found, update its fields.
//        if (existingRental != null) {
//            // Use the builder to create a new object with updated values.
//            existingRental = new Rental.RentalBuilder()
//                    .setRentalID(existingRental.getRentalID()) // Keep the original ID
//                    .setCarID(rental.getCarID())
//                    .setCustomerID(rental.getCustomerID())
//                    .setStartDate(rental.getStartDate())
//                    .setEndDate(rental.getEndDate())
//                    .setPickupLocation(rental.getPickupLocation())
//                    .setDropoffLocation(rental.getDropoffLocation())
//                    .setInsuranceID(rental.getInsuranceID())
//                    .setTotalCost(rental.getTotalCost())
//                    .setStatus(rental.getStatus())
//                    .build();
//
//            // 4. Save the new, updated rental object.
//            return repository.save(existingRental);
//        } else {
//            // 5. If no existing rental is found, return null.
//            return null;
//        }
//    }
//
//
//    // ===== Get All =====
//
//    public List<Rental> getAll() {
//        return this.repository.findAll();
//    }
//    // ===== Delete =====
//
//    public void delete(int id) {
//        this.repository.deleteById(id);
//    }
//
//
//}
