package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.SubscriptionBuilder;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestSubscriptionFactory {
    
    /*
     * Creates a test subscription that
     * can be used in Unit and integration tests.
     */
    public static Subscription create(boolean available) {
        LocalDateTime now = LocalDateTime.now();
        return new SubscriptionBuilder()
            .name(now.toString())
            .price(200)
            .days(180)
            .available(available)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a subscription is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(boolean available) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", now.toString());
        map.put("price", 200d);
        map.put("days", 180d);
        map.put("available", available);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
