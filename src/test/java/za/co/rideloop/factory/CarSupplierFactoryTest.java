package za.co.rideloop.factory;

import org.junit.jupiter.api.Test;
import za.co.rideloop.Domain.CarSupplier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
*CarSupplierFactoryTest.java
*CarSupplierFactoryTest model class
*@author : Swatsi Bongani Ratia
*@studnr : 230724477
*@group  : 3I
*@Java version: "21.0.3" 2024-04-16 LTS
*/

class CarSupplierFactoryTest {
    @Test
    void testBuildSuccess() {
        CarSupplier supplier = CarSupplierFactory.build(
                1,
                "AutoWorld",
                "Kruben Naidoo",
                new Date(),
                "Active");
        assertNotNull(supplier);
        assertEquals("AutoWorld", supplier.getName());
        assertEquals("Kruben Naidoo", supplier.getContactPerson());

        assertEquals("Active", supplier.getContractStatus());
    }
  @Test
    void testBuildWithEmptyName_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CarSupplierFactory.build(
                    2,
                    "",
                    "Swatsi Ratia",
                    new Date(),
                    "Pending");
        });
        //String expectedMessage = "Invalid Input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Invalid Input"));
  }
}