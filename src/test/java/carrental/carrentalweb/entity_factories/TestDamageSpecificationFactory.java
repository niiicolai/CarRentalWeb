package carrental.carrentalweb.entity_factories;

import carrental.carrentalweb.builder.DamageSpecificationBuilder;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.records.DatabaseRecord;

import java.time.LocalDateTime;
import java.util.HashMap;

// Mads
public class TestDamageSpecificationFactory {

    public static DamageSpecification create() {
        LocalDateTime now = LocalDateTime.now();
        return new DamageSpecificationBuilder()
                .description(now.toString())
                .price(100)
                .build();
    }

    public static DatabaseRecord createDatabaseRecord(boolean damaged) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("description", now.toString());
        map.put("damaged", damaged);
        map.put("price", 100d);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
