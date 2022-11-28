package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.services.DatabaseService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PickupPointRepository {

	private final DatabaseService databaseService;

	public PickupPointRepository(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void createPickupPoint(PickupPoint newPickupPoint) {
		try {
		Connection conn = databaseService.getConnection();
		String query = "INSERT INTO pickup_points (name, address_id) VALUES (?, ?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);

		preparedStatement.setString(1, newPickupPoint.getName());
		preparedStatement.setObject(2, newPickupPoint.getAddressId());

		preparedStatement.executeUpdate();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	}

	public List<PickupPoint> getPickupPointsList() {
		List<PickupPoint> pickupPoints = new ArrayList<>();
		try {
		Connection conn = databaseService.getConnection();
		String query = "SELECT * FROM pickup_points";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			long addressId = resultSet.getLong("address_id");
			LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
			LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");

			pickupPoints.add(
				new PickupPoint(id, name, addressId, createdAt, updatedAt)
			);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return pickupPoints;
	}

	public PickupPoint findPickupPointById(long id) {
		PickupPoint pickupPoint = new PickupPoint();

		try {
		Connection conn = databaseService.getConnection();
		String query = "SELECT * FROM pickup_points WHERE id = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setLong(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String name = resultSet.getString("name");
			long addressId = resultSet.getLong("address_id");
			LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
			LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");

			pickupPoint = new PickupPoint(id, name, addressId, createdAt, updatedAt);

		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return pickupPoint;
	}

	public void updatePickupPoint(PickupPoint pickupPoint) {
		try {
		Connection conn = databaseService.getConnection();
		String query = "UPDATE pickup_points SET address_id = ?, name = ? WHERE id = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setObject(1, pickupPoint.getAddressId());
		preparedStatement.setString(2, pickupPoint.getName());
		preparedStatement.setLong(3, pickupPoint.getId());
		System.out.println(preparedStatement);
		preparedStatement.executeUpdate();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	}

	public void delete(PickupPoint pickupPoint) {
		try {
			Connection conn = databaseService.getConnection();
			String query = "DELETE FROM pickup_points WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setObject(1, pickupPoint.getId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PickupPoint last() {
		PickupPoint pickup = null;

		try {
			Connection conn = databaseService.getConnection();
			String query = "SELECT * FROM pickup_points ORDER BY created_at DESC LIMIT 1";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				long addressId = resultSet.getLong("address_id");
				LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
				LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");

				pickup = new PickupPoint(id, name, addressId, createdAt, updatedAt);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pickup;
    }
}
