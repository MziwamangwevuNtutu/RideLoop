package za.co.rideloop.factory;


import za.co.rideloop.Domain.CarSupplier;

import java.util.Date;

/**
 * CarSupplierFactory.java
 * CarSupplierFactory model class
 *
 * @author : Swatsi Bongani Ratia
 * @studnr : 230724477
 * @group : 3I
 * @Java version: "21.0.3" 2024-04-16 LTS
 */

public class CarSupplierFactory {
    public static CarSupplier build(int supplierID, String name, String contactPerson, Date supplyDate, String contractStatus) {
        if(name == null || name.isEmpty() || contactPerson == null || contactPerson.isEmpty() || contractStatus == null || contractStatus.isEmpty()){
            throw new IllegalArgumentException("invalid input - fields must not be empty");
        }
        return new CarSupplier.Builder()
                .supplierID(supplierID)
                .name(name)
                .contactPerson(contactPerson)
                .supplyDate(supplyDate)
                .contractStatus(contractStatus)
                .build();
    }
}
