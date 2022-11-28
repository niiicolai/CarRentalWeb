package carrental.carrentalweb.repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceRepository {

    private final DatabaseService databaseService;

    public InvoiceRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Invoice find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoices WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponseFirst(databaseResponse);
    }
    
    public boolean insert(Invoice invoice) {
        String sql = "INSERT INTO invoices (booking_id, due_date, paid_at) VALUES (?, ?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(invoice.getBookingId(), invoice.getDueDate(), invoice.getPaidAt());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse.isSuccessful();
    }

    public Invoice last() {
        String sql = "SELECT * FROM invoices ORDER BY id DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }

    public boolean delete(Invoice invoice) {
        String sql = "DELETE FROM invoices WHERE id = ?";
        DatabaseRequestBody requestBody = new DatabaseRequestBody(invoice.getId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, requestBody);
        
        return databaseResponse.isSuccessful();
    }

    public Invoice parseResponseFirst(DatabaseResponse databaseResponse) {
        List<Invoice> invoices = parseResponse(databaseResponse);
        if (invoices.size() == 0) return null;
        else return invoices.get(0);
    }

    public List<Invoice> parseResponse(DatabaseResponse databaseResponse) {
        List<Invoice> invoices = new LinkedList<Invoice>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            invoices.add(
                new Invoice(
                    (long) record.map().get("id"),
                    (long) record.map().get("booking_id"),
                    (LocalDateTime) record.map().get("due_date"),
                    (LocalDateTime) record.map().get("paid_at")
                )
            );
        }

        return invoices;
    }

    
}
