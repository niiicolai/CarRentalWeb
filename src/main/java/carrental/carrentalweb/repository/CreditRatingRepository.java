package carrental.carrentalweb.repository;

/*
 * Written by Nicolai Berg Andersen.
 */

import org.springframework.stereotype.Repository;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;

@Repository
public class CreditRatingRepository {

    public CreditRating find(String column, Object value) {
        String sql = String.format("SELECT * FROM credit_ratings WHERE %s=?", column);
        
        /* TODO: FETCH CREDIT RATING */

        return new CreditRating(1, CreditRatingState.Reject);
    }
    
    public boolean insert(CreditRating creditRating) {
        String sql = "INSERT INTO credit_ratings (booking_id, state) (?, ?)";

        /* TODO: INSERT CREDIT RATING */

        return true;
    }
}
