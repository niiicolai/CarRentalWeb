package carrental.carrentalweb.entity_factories;

import carrental.carrentalweb.builder.DamageReportBuilder;
import carrental.carrentalweb.entities.DamageReport;
import carrental.carrentalweb.records.DatabaseRecord;
import java.time.LocalDateTime;
import java.util.HashMap;

// Mads
public class TestDamageReportFactory {

    public static DamageReport create(long bookingId) {
        return new DamageReportBuilder()
                .bookingId(bookingId)
                .build();
    }

    public static DatabaseRecord createDatabaseRecord(long bookingId) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("booking_id", bookingId);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
