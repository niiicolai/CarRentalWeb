package carrental.carrentalweb.repository;

import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import carrental.carrentalweb.entities.Invoice;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceRepository {
    public Invoice find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoices WHERE %s=?", column);
        
        /* TODO: Fetch from database */

        return new Invoice(1, LocalDateTime.now(), LocalDateTime.now());
    }
    
    public boolean insert(Invoice invoice) {
        String sql = "INSERT INTO invoices (booking_id, due, paid_at) (?, ?, ?)";

        return true;
    }

    public Invoice last() {
        String sql = String.format("SELECT * FROM invoices");
        
        /* TODO: Fetch from database */

        return new Invoice(1, LocalDateTime.now(), LocalDateTime.now());
    }
}
