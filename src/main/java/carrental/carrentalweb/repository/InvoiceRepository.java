package carrental.carrentalweb.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.services.DatabaseService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceRepository {

    @Autowired
    DatabaseService databaseService;

    public Invoice find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoices WHERE %s=?", column);
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(value);
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }
    
    public boolean insert(Invoice invoice) {
        String sql = "INSERT INTO invoices (booking_id, due, paid_at) (?, ?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(invoice.getBookingId());
        values.add(invoice.getDue());
        values.add(invoice.getPaidAt());

        databaseService.executeUpdate(sql, values);

        return true;
    }

    public Invoice last() {
        String sql = String.format("SELECT * FROM invoices ORDER BY created_at DESC LIMIT 1");
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    private Invoice parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new Invoice(
            (long) map.get("booking_id"),
            (LocalDateTime) map.get("due"),
            (LocalDateTime) map.get("paid_at")
        );
    }
}
