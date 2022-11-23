package carrental.carrentalweb.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.services.DatabaseService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class CreditRatingRepository {

    @Autowired
    DatabaseService databaseService;

    public CreditRating find(String column, Object value) {
        String sql = String.format("SELECT * FROM credit_ratings WHERE %s=?", column);
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(value);
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }
    
    public boolean insert(CreditRating creditRating) {
        String sql = "INSERT INTO credit_ratings (state, booking_id) (?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(creditRating.getState());
        values.add(creditRating.getBookingId());

        databaseService.executeUpdate(sql, values);

        return true;
    }

    public CreditRating last() {
        String sql = String.format("SELECT * FROM credit_ratings ORDER BY created_at DESC LIMIT 1");
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    private CreditRating parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new CreditRating(
            (CreditRatingState) map.get("state"),
            (Long) map.get("booking_id")
        );
    }
}
