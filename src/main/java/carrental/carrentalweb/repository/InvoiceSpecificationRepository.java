package carrental.carrentalweb.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.services.DatabaseService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class InvoiceSpecificationRepository {

    @Autowired
    DatabaseService databaseService;

    public InvoiceSpecification find(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(value);
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    public InvoiceSpecification[] findCollection(String column, Object value) {
        String sql = String.format("SELECT * FROM invoice_specifications WHERE %s=?", column);
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(value);
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);

        return parseFromList(resultList);
    }
    
    public boolean insert(InvoiceSpecification invoiceSpecification) {
        String sql = "INSERT INTO invoice_specifications (booking_id, description, price) (?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(invoiceSpecification.getBookingId());
        values.add(invoiceSpecification.getDescription());
        values.add(invoiceSpecification.getPrice());

        databaseService.executeUpdate(sql, values);

        return true;
    }

    public InvoiceSpecification last() {
        String sql = String.format("SELECT * FROM invoice_specifications ORDER BY created_at DESC LIMIT 1");
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    private InvoiceSpecification[] parseFromList(List<HashMap<String, Object>> list) {
        InvoiceSpecification[] specifications = new InvoiceSpecification[list.size()];
        for (int i = 0; i < list.size(); i++) {
            specifications[i] = parseFromMap(list.get(i));
        }
        return specifications;
    }

    private InvoiceSpecification parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new InvoiceSpecification(
            (long) map.get("booking_id"),
            (String) map.get("description")
        );
    }
}
