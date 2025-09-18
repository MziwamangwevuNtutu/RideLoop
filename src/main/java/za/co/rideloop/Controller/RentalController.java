package za.co.rideloop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.rideloop.Domain.Rental;
import za.co.rideloop.Service.RentalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "http://localhost:3000")
public class RentalController {

    private final RentalService service;

    @Autowired
    public RentalController(RentalService service) {
        this.service = service;
    }

    // ===== CREATE =====
    @PostMapping("/create")
    public Rental create(@RequestBody Rental rental) {
        return service.create(rental);
    }

    // ===== READ =====
    @GetMapping("/read/{id}")
    public Rental read(@PathVariable Integer id) {
        return service.read(id).orElse(null);
    }

    // ===== UPDATE =====
    @PutMapping("/update/{id}")
    public Rental update(@PathVariable Integer id, @RequestBody Rental rentalDetails) {
        return service.update(id, rentalDetails);
    }

    // ===== DELETE =====
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // ===== GET ALL =====
    @GetMapping("/getAll")
    public List<Rental> getAll() {
        return service.getAll();
    }
}


//public class RentalController {
//
//    private final RentalService rentalService;
//
//    @Autowired
//    public RentalController(RentalService rentalService) {
//        this.rentalService = rentalService;
//    }
//
//    // ===== CREATE =====
//    @PostMapping
//    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
//        Rental newRental = rentalService.create(rental);
//        return ResponseEntity.ok(newRental);
//    }
//
//    // ===== READ =====
//    @GetMapping("/{id}")
//    public ResponseEntity<Rental> getRentalById(@PathVariable int id) {
//        Optional<Rental> rental = rentalService.read(id);
//        return rental.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // ===== UPDATE =====
//    @PutMapping("/{id}")
//    public ResponseEntity<Rental> updateRental(@PathVariable int id, @RequestBody Rental rentalDetails) {
//        Rental updatedRental = rentalService.update(id, rentalDetails);
//        if (updatedRental != null) {
//            return ResponseEntity.ok(updatedRental);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // ===== DELETE =====
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRental(@PathVariable int id) {
//        rentalService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // ===== GET ALL =====
//    @GetMapping
//    public ResponseEntity<List<Rental>> getAllRentals() {
//        List<Rental> rentals = rentalService.getAll();
//        return ResponseEntity.ok(rentals);
//    }
//}