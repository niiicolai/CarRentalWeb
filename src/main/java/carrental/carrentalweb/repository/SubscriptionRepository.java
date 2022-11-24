package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.services.DatabaseService;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// Mads
@Repository
public class SubscriptionRepository {

    private final DatabaseService databaseService;

    public SubscriptionRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Subscription find(String column, Object value) {
        String sql = String.format("SELECT * FROM subscriptions WHERE %s=?", column);

        LinkedList<Object> values = new LinkedList<>();
        values.add(value);

        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    public boolean create(Subscription subscription) {
        String query = "INSERT INTO subscriptions (" +
                "name," +
                "days," +
                "price," +
                "available," +
                "created_at," +
                "updated_at)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(subscription.getName());
        values.add(subscription.getDays());
        values.add(subscription.getPrice());
        values.add(subscription.isAvailable());
        values.add(subscription.getCreatedAt());
        values.add(subscription.getUpdatedAt());

        databaseService.executeUpdate(query, values);
        return true;
    }
    public List<Subscription> getAll() {

        List<Subscription> subscriptions = new ArrayList<>();

        String query = "SELECT * FROM subscriptions";

        List<HashMap<String, Object>> resultList = databaseService.executeQuery(query, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromList(resultList);
    }
    public void update(Subscription subscription){
        try {
            Connection conn = databaseService.getConnection();
            String query = "UPDATE subscriptions " +
                    "SET available=?," +
                    "updated_at=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setBoolean(1, subscription.isAvailable());
            preparedStatement.setObject(2, LocalDateTime.now());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Subscription parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new Subscription(
                (String) map.get("name"),
                (Double) map.get("days"),
                (Double) map.get("price"),
                (Boolean) map.get("available"),
                (LocalDateTime) map.get("created_at"),
                (LocalDateTime) map.get("updated_at")
        );
    }
    private List<Subscription> parseFromList(List<HashMap<String, Object>> list) {
        List<Subscription> subscriptions = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            subscriptions.add(parseFromMap(map));
        }
        return subscriptions;
    }
}
