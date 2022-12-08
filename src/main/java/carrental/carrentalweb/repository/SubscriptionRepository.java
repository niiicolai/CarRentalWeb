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
        String query = String.format("SELECT * FROM subscriptions WHERE %s=?", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, body);
        return parseResponseFirst(databaseResponse);
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
    public boolean update(Subscription subscription) {
        String query = "UPDATE subscriptions SET price=?, days=?, available=? WHERE name=?";
        DatabaseRequestBody body = new DatabaseRequestBody(subscription.getPrice(), subscription.getDays(),
            subscription.isAvailable(), subscription.getName());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
        return databaseResponse.isSuccessful();
    }
    public boolean delete(Subscription subscription) {
        String query = "DELETE FROM subscriptions WHERE name=?";
        DatabaseRequestBody requestBody = new DatabaseRequestBody(subscription.getName());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
        return databaseResponse.isSuccessful();
    }
    public Subscription last() {
        String query = "SELECT * FROM subscriptions ORDER BY created_at DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
        return parseResponseFirst(databaseResponse);
    }

    public List<Subscription> last(int number) {
		String sql = "SELECT * FROM subscriptions ORDER BY created_at DESC LIMIT %d";
		DatabaseResponse databaseResponse = databaseService.executeQuery(String.format(sql, number), new DatabaseRequestBody());
		return parseResponse(databaseResponse);
	}
    
    public Subscription parseResponseFirst(DatabaseResponse databaseResponse) {
        List<Subscription> subscriptions = parseResponse(databaseResponse);
        if (subscriptions.size() == 0) return null;
        else return subscriptions.get(0);
    }
    public List<Subscription> parseResponse(DatabaseResponse databaseResponse) {
        List<Subscription> subscriptions = new LinkedList<>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();
            subscriptions.add(
                new Subscription(
                    (String) record.map().get("name"),
                    (double) record.map().get("days"),
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
