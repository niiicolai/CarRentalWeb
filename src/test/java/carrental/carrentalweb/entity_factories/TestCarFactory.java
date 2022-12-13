package carrental.carrentalweb.entity_factories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import carrental.carrentalweb.builder.CarBuilder;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestCarFactory {

    private static int number = 1;

    /*
     * Creates a test car that
     * can be used in Unit and integration tests.
     */

    //Mikkel
    public static Car create(boolean inspected) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);
        now = LocalDateTime.parse(formatDateTime, formatter);

        return new CarBuilder()
            .vehicleNumber(0)
            .frameNumber(now.toString())
            .brand(now.toString())
            .model(now.toString())
            .color(now.toString())
            .equipmentLevel(number++)
            .steelPrice(number++)
            .registrationFee(number++)
            .co2Discharge(number++)
            .inspected(inspected)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a car is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(boolean inspected) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("vehicle_number", 0);
        map.put("frame_number", now.toString());
        map.put("brand", now.toString());
        map.put("model", now.toString());
        map.put("color", now.toString());
        map.put("equipmentLevel", 1);
        map.put("steelPrice", 200);
        map.put("registrationFee", 200);
        map.put("co2Discharge", 200);
        map.put("inspected", inspected);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
