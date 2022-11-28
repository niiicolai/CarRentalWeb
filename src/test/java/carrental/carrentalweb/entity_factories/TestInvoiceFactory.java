package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.InvoiceBuilder;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestInvoiceFactory {
    
    /*
     * Creates a test invoice that
     * can be used in Unit and integration tests.
     */
    public static Invoice create(long bookingId) {
        LocalDateTime now = LocalDateTime.now();
        return new InvoiceBuilder()
            .id(0)
            .bookingId(bookingId)
            .due(now)
            .paidAt(now)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a invoice is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(long bookingId) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0L);
        map.put("booking_id", bookingId);
        map.put("due_date", now);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
