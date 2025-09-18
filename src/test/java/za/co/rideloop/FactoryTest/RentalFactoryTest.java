package za.co.rideloop.FactoryTest;
/**
 * Admin.java
 * Admin Model Class
 *
 * @Author: Mziwamangwevu Ntutu
 * @Student Number: 217054420
 * Group 3 I
 **/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.rideloop.Domain.Car;
import za.co.rideloop.Domain.CustomerProfile;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Factory.RentalFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalFactoryTest {
    private Car car;
    private CustomerProfile customerProfile;

    @BeforeEach
    void setUp() {
        // Create a dummy Car object for testing
        car = new Car.Builder()
                .setCarId(1)
                .setBrand("Toyota")
                .setModel("Corolla")
                .build();

        // Create a dummy CustomerProfile object for testing
        customerProfile = new CustomerProfile.Builder()
                //.setCustomerId(1)
                .setFirstName("John")
                .setLastName("Doe")
                .build();
    }
    @Test
    void createRental_validInput_returnsRentalObject() {

        LocalDate startDate = LocalDate.of(2025, 10, 1);
        LocalDate endDate = LocalDate.of(2025, 10, 5);
        String pickupLocation = "Cape Town";
        String dropoffLocation = "Johannesburg";
        int insuranceId = 123;
        double totalCost = 500.00;
        String status = "Active";


        Rental rental = RentalFactory.createRental(
                car,
                customerProfile,
                startDate,
                endDate,
                pickupLocation,
                dropoffLocation,
                insuranceId,
                totalCost,
                status
        );


        assertNotNull(rental);
        assertEquals(car, rental.getCar());
        assertEquals(customerProfile, rental.getCustomerProfile());
        assertEquals(startDate, rental.getStartDate());
        assertEquals(endDate, rental.getEndDate());
        assertEquals(pickupLocation, rental.getPickupLocation());
        assertEquals(dropoffLocation, rental.getDropoffLocation());
        assertEquals(insuranceId, rental.getInsuranceID());
        assertEquals(totalCost, rental.getTotalCost());
        assertEquals(status, rental.getStatus());
        System.out.println(rental);
    }

}