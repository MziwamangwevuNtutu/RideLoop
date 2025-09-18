package za.co.rideloop.ServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import za.co.rideloop.Domain.Car;
import za.co.rideloop.Domain.CustomerProfile;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Factory.RentalFactory;
import za.co.rideloop.Service.RentalService;
import za.co.rideloop.Repository.RentalRepository;
import za.co.rideloop.Repository.CarRepository;
import za.co.rideloop.Repository.CustomerProfileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RentalServiceTest {

    @Autowired
    private RentalService service;

    @Autowired
    private RentalRepository repository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    private Car testCar;
    private CustomerProfile testCustomer;

    @BeforeEach
    void setUp() {
        // --- Setup Car ---
        testCar = new Car.Builder()
                .setBrand("Toyota")
                .setModel("Corolla")
                .setYear(2022)
                .setLicensePlate("CA12345")
                .setRentalRate(500.0)
                .setStatus("Available")
                .setCategory("Sedan")
                .setMileage(15000)
                .setLastMaintenance("2025-01-01")
                .setMaintenanceDue("2025-06-01")
                .build();
        testCar = carRepository.save(testCar);

        // --- Setup CustomerProfile ---
        testCustomer = new CustomerProfile.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setPhoneNumber("0123456789")
                .setLicenseNumber("L123456")
                .setIdNumber("8001015009087")
                .build();
        testCustomer = customerProfileRepository.save(testCustomer);
    }

    // ===== CREATE =====
    @Test
    @Commit
    void create() {
        Rental newRental = RentalFactory.createRental(
                testCar,
                testCustomer,
                LocalDate.of(202, 11, 1),
                LocalDate.of(2025, 11, 2),
                "Blomfontein",
                "Vosloorus",
                303,
                900.00,
                "Booked"
        );

        Rental saved = service.create(newRental);

        assertNotNull(saved);
        assertNotEquals(0, saved.getRentalID()); // ID should be generated
        assertEquals("Booked", saved.getStatus());

        System.out.println("Created Rental: " + saved);
    }

    // ===== READ =====
    @Test
    @Commit
    void read() {
        Rental newRental = RentalFactory.createRental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 2),
                "Cape Town",
                "Worcester",
                303,
                900.00,
                "Booked"
        );
        Rental saved = service.create(newRental);

        Optional<Rental> foundOptional = service.read(saved.getRentalID());

        assertTrue(foundOptional.isPresent());
        assertEquals(saved.getRentalID(), foundOptional.get().getRentalID());

        System.out.println("Found Rental: " + foundOptional.get());
    }

    // ===== UPDATE =====
    @Test
    @Commit
    void update() {
        Rental newRental = RentalFactory.createRental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 2),
                "Cape Point",
                "WaterFront",
                303,
                900.00,
                "Booked"
        );
        Rental saved = service.create(newRental);

        Rental updatedRentalData = new Rental.RentalBuilder()
                .RentalBuilderCopy(saved)
                .setStatus("Completed")
                .setTotalCost(600.50)
                .build();

        Rental result = service.update(saved.getRentalID(), updatedRentalData);

        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        assertEquals(600.50, result.getTotalCost());

        System.out.println("Updated Rental: " + result);
    }

    // ===== GET ALL =====
    @Test
    @Commit
    void getAll() {
        Rental firstRental = RentalFactory.createRental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 2),
                "Cape Town",
                "Worcester",
                303,
                900.00,
                "Booked"
        );
        service.create(firstRental);

        Car secondCar = new Car.Builder()
                .setBrand("Ford")
                .setModel("Figo")
                .setYear(2020)
                .setLicensePlate("CJ98765")
                .setRentalRate(400.0)
                .setStatus("Available")
                .setCategory("Hatchback")
                .setMileage(8000)
                .setLastMaintenance("2025-02-01")
                .setMaintenanceDue("2025-07-01")
                .build();
        secondCar = carRepository.save(secondCar);

        CustomerProfile secondCustomer = new CustomerProfile.Builder()
                .setFirstName("Jane")
                .setLastName("Smith")
                .setPhoneNumber("0987654321")
                .setLicenseNumber("L654321")
                .setIdNumber("9002026009088")
                .build();
        secondCustomer = customerProfileRepository.save(secondCustomer);

        Rental secondRental = RentalFactory.createRental(
                secondCar,
                secondCustomer,
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 10),
                "Johannesburg",
                "Durban",
                302,
                1200.00,
                "Confirmed"
        );
        service.create(secondRental);

        List<Rental> all = service.getAll();
        assertTrue(all.size() >= 2);

        System.out.println("All Rentals: " + all);
    }

    // ===== DELETE =====
    @Test
    @Commit
    void delete_removesRecord() {
        Rental newRental = RentalFactory.createRental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 2),
                "Cape Town",
                "Worcester",
                303,
                900.00,
                "Booked"
        );
        Rental saved = service.create(newRental);

        int idToDelete = saved.getRentalID();
        service.delete(idToDelete);

        Optional<Rental> deletedRental = repository.findById(idToDelete);
        assertFalse(deletedRental.isPresent());

        System.out.println("Deleted Rental with ID: " + idToDelete);
    }
}
