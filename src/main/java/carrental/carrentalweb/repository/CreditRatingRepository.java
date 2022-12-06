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
        return parseResponseFirst(databaseResponse);
    }
    
    public DatabaseResponse insert(CreditRating creditRating) {
        String sql = "INSERT INTO credit_ratings (state, user_id) VALUES (?, ?)";        
        DatabaseRequestBody body = new DatabaseRequestBody(creditRating.getState().name(), creditRating.getUserId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse;
    }

    public DatabaseResponse update(CreditRating creditRating) {
        String sql = "UPDATE credit_ratings SET state = ? WHERE user_id = ?";
        DatabaseRequestBody body = new DatabaseRequestBody(creditRating.getState().name(), creditRating.getUserId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        return databaseResponse;
    }

    public CreditRating last() {
        String sql = "SELECT * FROM credit_ratings ORDER BY created_at DESC LIMIT 1";        
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }

    public DatabaseResponse delete(CreditRating creditRating) {
        String sql = "DELETE FROM credit_ratings WHERE user_id = ?";
        DatabaseRequestBody requestBody = new DatabaseRequestBody(creditRating.getUserId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, requestBody);
        
        return databaseResponse;
    }

    public CreditRating parseResponseFirst(DatabaseResponse databaseResponse) {
        List<CreditRating> creditRatings = parseResponse(databaseResponse);
        if (creditRatings.size() == 0) return null;
        else return creditRatings.get(0);
    }

    public List<CreditRating> parseResponse(DatabaseResponse databaseResponse) {
        List<CreditRating> creditRatings = new LinkedList<CreditRating>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();
            
            creditRatings.add(
                new CreditRating(
                    CreditRatingState.valueOf((String) record.map().get("state")),
                    (Long) record.map().get("user_id")
                )
            );
        }

        return creditRatings;
    }
}
