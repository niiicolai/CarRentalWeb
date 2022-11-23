package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.entities.PickupPoint;
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
public class DamageSpecificationRepository {
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public List<DamageSpecification> getAll() {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        List<DamageSpecification> specifications = new ArrayList<>();
        try {
            String query = "SELECT * FROM damage_specifications";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String description = resultSet.getString(1);
                boolean damaged = resultSet.getBoolean(2);
                double price = resultSet.getDouble(3);
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject(4);
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject(5);
                specifications.add(new DamageSpecification(
                        description,
                        damaged,
                        price,
                        createdAt,
                        updatedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specifications;
    }

    public void create(DamageSpecification dmgSpec) {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

        try {
            String query = "INSERT INTO damage_specifications (" +
                    "description," +
                    "damaged," +
                    "price," +
                    "created_at," +
                    "updated_at)" +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, dmgSpec.getDescription());
            preparedStatement.setBoolean(2, dmgSpec.isDamaged());
            preparedStatement.setDouble(3, dmgSpec.getPrice());
            preparedStatement.setObject(4, LocalDateTime.now());
            preparedStatement.setObject(5, LocalDateTime.now());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(DamageSpecification dmgSpec) {

        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        try {
            String query = "UPDATE damage_specifications " +
                    "SET damaged=?," +
                    "price=?," +
                    "updated_at=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setBoolean(1, dmgSpec.isDamaged());
            preparedStatement.setDouble(2, dmgSpec.getPrice());
            preparedStatement.setObject(3, LocalDateTime.now());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DamageSpecification getByDescription(String description) {
        DamageSpecification dmgSpec = new DamageSpecification();

        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

        try {
            String query = "SELECT * FROM damage_specifications WHERE description=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, description);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String desc = resultSet.getString(1);
                boolean damaged = resultSet.getBoolean(2);
                double price = resultSet.getDouble(3);
                LocalDateTime createdAt =  (LocalDateTime) resultSet.getObject(4);
                LocalDateTime updatedAt =  (LocalDateTime) resultSet.getObject(5);

                dmgSpec = new DamageSpecification(desc,
                        damaged,
                        price,
                        createdAt,
                        updatedAt);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return dmgSpec;
    }
}
