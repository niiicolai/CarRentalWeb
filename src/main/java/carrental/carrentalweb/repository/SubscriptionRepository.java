package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.utilities.MySQLConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Mads
@Repository
public class SubscriptionRepository {
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    // TODO: create find method
    public Subscription find(String subscriptionName) {
        return new Subscription();
    }

    public Subscription create(Subscription subscription) {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

        try {
            String query = "INSERT INTO subscriptions (" +
                    "name," +
                    "days," +
                    "price," +
                    "available," +
                    "created_at," +
                    "updated_at)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, subscription.getName());
            preparedStatement.setDouble(2, subscription.getDays());
            preparedStatement.setDouble(3, subscription.getPrice());
            preparedStatement.setBoolean(4, subscription.isAvailable());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.setObject(6, LocalDateTime.now());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscription;
    }
    public List<Subscription> getAll() {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            String query = "SELECT * FROM subscriptions";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                double days = resultSet.getDouble(2);
                double price = resultSet.getDouble(3);
                boolean available = resultSet.getBoolean(4);
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject(5);
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject(6);
                subscriptions.add(new Subscription(
                        name,
                        days,
                        price,
                        available,
                        createdAt,
                        updatedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }
    public void update(Subscription subscription){
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        try {
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
}
