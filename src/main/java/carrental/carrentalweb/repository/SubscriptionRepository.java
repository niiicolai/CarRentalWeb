package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// Mads
@Repository
public class SubscriptionRepository {

    private final DatabaseService databaseService;

    public SubscriptionRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Subscription get(String column, Object value) {
        String sql = String.format("SELECT * FROM subscriptions WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        return parseResponse(databaseResponse).get(0);
    }
    public List<Subscription> getAll() {
        String query = "SELECT * FROM subscriptions";
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
        return parseResponse(databaseResponse);
    }

    public boolean create(Subscription subscription) {
        String query = "INSERT INTO subscriptions (name, days, price, available) VALUES (?, ?, ?, ?)";
        DatabaseRequestBody body = new DatabaseRequestBody(subscription.getName(), subscription.getDays(),
            subscription.getPrice(), subscription.isAvailable());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }
    public boolean update(Subscription subscription){
        String query = "UPDATE subscriptions SET available = ?, price = ?, days = ?, available = ? WHERE name = ?";
        DatabaseRequestBody body = new DatabaseRequestBody(subscription.getPrice(), subscription.getDays(),
            subscription.isAvailable(), subscription.getName());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }

    private List<Subscription> parseResponse(DatabaseResponse databaseResponse) {
        List<Subscription> subscriptions = new LinkedList<Subscription>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            subscriptions.add(
                new Subscription(
                    (String) record.map().get("name"),
                    (long) record.map().get("days"),
                    (double) record.map().get("price"),
                    (boolean) record.map().get("available"),
                    (LocalDateTime) record.map().get("created_at"),
                    (LocalDateTime) record.map().get("updated_at")
                )
            );
        }
        return subscriptions;
    }
}
