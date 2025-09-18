package za.co.rideloop.Factory;
/**
 * RentalFactory.java
 * RentalFactory Model Class
 *
 * @Author: Mziwamangwevu Ntutu
 * @Student Number: 217054420
 * Group 3 I
 **/
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Util.Helper;

import java.time.LocalDate;



import za.co.rideloop.Domain.Car;
import za.co.rideloop.Domain.CustomerProfile;
import za.co.rideloop.Domain.Rental;
import java.time.LocalDate;

public class RentalFactory {

    public static Rental createRental(
            Car car,
            CustomerProfile customerProfile,
            LocalDate startDate,
            LocalDate endDate,
            String pickupLocation,
            String dropoffLocation,
            int insuranceID,
            double totalCost,
            String status) {

        return new Rental.RentalBuilder()
                .setCar(car)
                .setCustomerProfile(customerProfile)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropoffLocation(dropoffLocation)
                .setInsuranceID(insuranceID)
                .setTotalCost(totalCost)
                .setStatus(status)
                .build();
    }
}