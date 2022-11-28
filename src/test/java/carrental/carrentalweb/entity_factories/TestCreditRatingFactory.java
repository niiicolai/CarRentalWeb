package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.CreditRatingBuilder;
import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestCreditRatingFactory {
    
    /*
     * Creates a test credit rating that
     * can be used in Unit and integration tests.
     */
    public static CreditRating create(CreditRatingState state, long userId) {
        return new CreditRatingBuilder().state(state).userId(userId).build();
    }
    
    /*
     * Creates a database record with random data
     * matching the data a credit rating is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createDatabaseRecord(CreditRatingState state, long userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("state", state.toString());
        map.put("created_at", LocalDateTime.now());
        map.put("updated_at", LocalDateTime.now());
        return new DatabaseRecord(map);
    }

}
