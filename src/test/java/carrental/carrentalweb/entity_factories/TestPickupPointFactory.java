package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.PickupPointBuilder;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestPickupPointFactory {
    
    /*
     * Creates a test pickup that
     * can be used in Unit and integration tests.
     */
    public static PickupPoint create(long addressId) {
        LocalDateTime now = LocalDateTime.now();
        return new PickupPointBuilder()
            .id(0)
            .name(now.toString())
            .addressId(addressId)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a pickup is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(long addressId) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("name", now.toString());
        map.put("address_id", addressId);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
