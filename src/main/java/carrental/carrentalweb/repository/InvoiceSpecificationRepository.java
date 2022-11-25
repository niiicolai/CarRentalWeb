package carrental.carrentalweb.repository;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceSpecificationRepository {

    private final DatabaseService databaseService;

    public InvoiceSpecificationRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public InvoiceSpecification find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponse(databaseResponse).get(0);
    }

    public List<InvoiceSpecification> findCollection(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponse(databaseResponse);
    }
    
    public boolean insert(InvoiceSpecification specification) {
        String sql = "INSERT INTO invoice_specifications (booking_id, description, price) (?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(specification.getBookingId(), specification.getDescription(), 
            specification.getPrice());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse.isSuccessful();
    }

    public InvoiceSpecification last() {
        String sql = "SELECT * FROM invoice_specifications ORDER BY created_at DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponse(databaseResponse).get(0);
    }

    private List<InvoiceSpecification> parseResponse(DatabaseResponse databaseResponse) {
        List<InvoiceSpecification> invoices = new LinkedList<InvoiceSpecification>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            invoices.add(
                new InvoiceSpecification(
                    (long) record.map().get("booking_id"),
                    (String) record.map().get("description")
                )
            );
        }
        return invoices;
    }
}
