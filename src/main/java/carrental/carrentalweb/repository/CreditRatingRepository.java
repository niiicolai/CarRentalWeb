package carrental.carrentalweb.repository;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class CreditRatingRepository {

    private final DatabaseService databaseService;

    public CreditRatingRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public CreditRating find(String column, Object value) {
        String sql = String.format("SELECT * FROM credit_ratings WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponse(databaseResponse).get(0);
    }
    
    public boolean insert(CreditRating creditRating) {
        String sql = "INSERT INTO credit_ratings (state, booking_id) (?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(creditRating.getState(), creditRating.getBookingId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse.isSuccessful();
    }

    public CreditRating last() {
        String sql = "SELECT * FROM credit_ratings ORDER BY created_at DESC LIMIT 1";        
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponse(databaseResponse).get(0);
    }

    private List<CreditRating> parseResponse(DatabaseResponse databaseResponse) {
        List<CreditRating> creditRatings = new LinkedList<CreditRating>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            creditRatings.add(
                new CreditRating(
                    (CreditRatingState) record.map().get("state"),
                    (Long) record.map().get("booking_id")
                )
            );
        }

        return creditRatings;
    }
}
