package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.InvoiceSpecificationBuilder;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestInvoiceSpecificationFactory {
    
    /*
     * Creates a test invoice specification that
     * can be used in Unit and integration tests.
     */
    public static InvoiceSpecification create(long invoiceId) {
        LocalDateTime now = LocalDateTime.now();
        return new InvoiceSpecificationBuilder()
            .id(0)
            .invoiceId(invoiceId)
            .description(now.toString())
            .price(100.0)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a invoice specification is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(long invoiceId) {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 0L);
        map.put("invoice_id", invoiceId);
        map.put("description", now.toString());
        map.put("price", 100.0);
        map.put("created_at", now);
        map.put("updated_at", now);
        return new DatabaseRecord(map);
    }
}
