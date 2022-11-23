package carrental.carrentalweb.repository;

import org.springframework.stereotype.Repository;

import carrental.carrentalweb.entities.InvoiceSpecification;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceSpecificationRepository {
    public InvoiceSpecification find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        
        /* TODO: FETCH INVOICE SPECIFICATION */

        return new InvoiceSpecification(1, "");
    }
    
    public boolean insert(InvoiceSpecification invoiceSpecification) {
        String sql = "INSERT INTO invoice_specifications (booking_id, description, price) (?, ?)";

        /* TODO: INSERT INVOICE SPECIFICATION */

        return true;
    }
}
