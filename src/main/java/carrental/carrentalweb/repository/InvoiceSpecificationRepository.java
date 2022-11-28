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
        return parseResponseFirst(databaseResponse);
    }

    public List<InvoiceSpecification> findCollection(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponse(databaseResponse);
    }
    
    public boolean insert(InvoiceSpecification specification) {
        String sql = "INSERT INTO invoice_specifications (invoice_id, description, price) VALUES (?, ?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(specification.getInvoiceId(), specification.getDescription(), 
            specification.getPrice());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse.isSuccessful();
    }

    public InvoiceSpecification last() {
        String sql = "SELECT * FROM invoice_specifications ORDER BY id DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }

    public boolean delete(InvoiceSpecification specification) {
        String sql = "DELETE FROM invoice_specifications WHERE id = ?";
        DatabaseRequestBody requestBody = new DatabaseRequestBody(specification.getId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, requestBody);
        
        return databaseResponse.isSuccessful();
    }

    public InvoiceSpecification parseResponseFirst(DatabaseResponse databaseResponse) {
        List<InvoiceSpecification> specifications = parseResponse(databaseResponse);
        if (specifications.size() == 0) return null;
        else return specifications.get(0);
    }

    public List<InvoiceSpecification> parseResponse(DatabaseResponse databaseResponse) {
        List<InvoiceSpecification> specifications = new LinkedList<InvoiceSpecification>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            specifications.add(
                new InvoiceSpecification(
                    (long) record.map().get("id"),
                    (long) record.map().get("invoice_id"),
                    (String) record.map().get("description"),
                    (double) record.map().get("price")
                )
            );
        }
        return specifications;
    }
}
