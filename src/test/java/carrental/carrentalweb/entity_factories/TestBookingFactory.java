package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.BookingBuilder;
import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestBookingFactory {
    
    /*
     * Creates a test booking that
     * can be used in Unit and integration tests.
     */
    public static Booking create(long pickupPointId, String subscriptionName, long userId, long vehicleNumber) {
        LocalDateTime now = LocalDateTime.now();
        return new BookingBuilder()
            .id(0)
            .deliveredAt(now)
            .pickupPointId(pickupPointId)
            .subscriptionName(subscriptionName)
            .userId(userId)
            .vehicleNumber(vehicleNumber)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a booking is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(long pickupPointId, String subscriptionName, long userId, long vehicleNumber) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("pickup_point_id", pickupPointId);
        map.put("subscription_name", subscriptionName);
        map.put("user_id", userId);
        map.put("vehicle_number", vehicleNumber);
        map.put("delivered_at", now);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
