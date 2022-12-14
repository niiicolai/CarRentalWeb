package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;

/*
 * Written by Thomas S. Andersen
 */
@Repository
public class PickupPointRepository {

	private final DatabaseService databaseService;

	public PickupPointRepository(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public boolean createPickupPoint(PickupPoint newPickupPoint) {
		String query = "INSERT INTO pickup_points (name, address_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
		/*newPickupPoint.setCreatedAt(LocalDateTime.now());
		newPickupPoint.setUpdatedAt(LocalDateTime.now());*/
		//Laves til et array af objects i DatabaseRequestBody
		DatabaseRequestBody requestBody = new DatabaseRequestBody(
				newPickupPoint.getName(),
				newPickupPoint.getAddressId(),
				LocalDateTime.now(),
				LocalDateTime.now());

		//databaseService kører executeUpdate med query'en og requestBody som er en Object array der er blevet iterated
		//i DatabaseRequestBody. I executeUpdate metoden. De tilføjes som arguments i et prepared statement med query string.
		//De objects, eller her både objects (LocalDateTime) og primitive data types (her Long og String), indføres så i rækkefølge
		//som angivet i den query der er defineret.
		DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
		return databaseResponse.isSuccessful();

		/*try {
		Connection conn = databaseService.getConnection();
		String query = "INSERT INTO pickup_points (name, address_id) VALUES (?, ?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);

		preparedStatement.setString(1, newPickupPoint.getName());
		preparedStatement.setObject(2, newPickupPoint.getAddressId());

		preparedStatement.executeUpdate();
		} catch (SQLException e) {
		e.printStackTrace();
		}*/
	}

	public List<PickupPoint> getPickupPointsList() {
		/* 
		 * Jeg har tilføjer "INNER JOIN address ON pickup_points.address_id=address.id"
		 * for at få adresse informationen med.
		 * ~ Nicolai
		 */
		String query = "SELECT * FROM pickup_points INNER JOIN address ON pickup_points.address_id=address.id";
		DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
		return parseResponse(databaseResponse);
		/*
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
		return pickupPoints;*/
	}
	

	public PickupPoint findPickupPointById(Long id) {
		String sql = "SELECT * FROM pickup_points INNER JOIN address ON pickup_points.address_id=address.id WHERE pickup_points.id= ?";
		DatabaseRequestBody body = new DatabaseRequestBody(id);
		DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
		return parseResponseFirst(databaseResponse);
		/*
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
		return pickupPoint;*/
	}

	public boolean updatePickupPoint(PickupPoint pickupPoint) {
		String query = "UPDATE pickup_points SET address_id=?, name=?, updated_at=? WHERE id = ?";
		DatabaseRequestBody body = new DatabaseRequestBody(pickupPoint.getAddressId(), pickupPoint.getName(), LocalDateTime.now(), pickupPoint.getId());
		DatabaseResponse databaseResponse = databaseService.executeUpdate(query, body);
		return databaseResponse.isSuccessful();

/*		try {
		Connection conn = databaseService.getConnection();
		String query = "UPDATE pickup_points SET address_id = ?, name = ? WHERE id = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setLong(1, pickupPoint.getAddressId());
		preparedStatement.setString(2, pickupPoint.getName());
		preparedStatement.setLong(3, pickupPoint.getId());
		System.out.println(preparedStatement);
		preparedStatement.executeUpdate();
		} catch (SQLException e) {
		e.printStackTrace();
		}*/
	}

	public boolean delete(PickupPoint pickupPoint) {
		String sql = "DELETE FROM pickup_points WHERE id = ?";
		DatabaseRequestBody requestBody = new DatabaseRequestBody(pickupPoint.getId());
		DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, requestBody);

		return databaseResponse.isSuccessful();

/*		try {
			Connection conn = databaseService.getConnection();
			String query = "DELETE FROM pickup_points WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setObject(1, pickupPoint.getId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	public PickupPoint last() {
/*		PickupPoint pickup = null;*/
		String sql = "SELECT * FROM pickup_points ORDER BY created_at DESC LIMIT 1";
		DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
		return parseResponseFirst(databaseResponse);
/*		try {
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

		return pickup;*/
    }

	public List<PickupPoint> last(int number) {
		String sql = "SELECT * FROM pickup_points INNER JOIN address ON pickup_points.address_id=address.id ORDER BY pickup_points.created_at DESC LIMIT %d";
		DatabaseResponse databaseResponse = databaseService.executeQuery(String.format(sql, number), new DatabaseRequestBody());
		return parseResponse(databaseResponse);
	}

	public PickupPoint parseResponseFirst(DatabaseResponse databaseResponse) {
		List<PickupPoint> pickupPoints = parseResponse(databaseResponse);
		if (pickupPoints.size() == 0) return null;
		else return pickupPoints.get(0);
	}

	private List<PickupPoint> parseResponse(DatabaseResponse databaseResponse) {
		List<PickupPoint> pickuppoints = new LinkedList<PickupPoint>();
		while (databaseResponse.hasNext()) {
			DatabaseRecord record = databaseResponse.next();

			/* 
			* Jeg har tilføjet parsing af en adresse her.
			* ~ Nicolai
			*/
			Address address = new Address(
				(String) record.map().get("street"),
              	(String) record.map().get("city"),
              	(String) record.map().get("zipCode"),
              	(String) record.map().get("country"),
				(Double) record.map().get("latitude"),
				(Double) record.map().get("longitude"));

			pickuppoints.add(
					new PickupPoint(
							(Long) record.map().get("id"),
							(String) record.map().get("name"),
							(long) record.map().get("address_id"),
							(LocalDateTime) record.map().get("created_at"),
							(LocalDateTime) record.map().get("updated_at"),
							address

			));
		}

		return pickuppoints;
	}
}
