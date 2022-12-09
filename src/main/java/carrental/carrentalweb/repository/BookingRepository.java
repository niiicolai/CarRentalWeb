package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/*
 * Written by Thomas S. Andersen.
 */

@Repository
public class BookingRepository {

  private final DatabaseService databaseService;

  public BookingRepository(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  public boolean createBooking(Booking newBooking) {

    String query = "INSERT INTO bookings (user_id, vehicle_number, subscription_name, pickup_point_id, delivered_at, returned_at, kilometer_driven) VALUES (?, ?, ?, ?, ?, ?, ?)";

    DatabaseRequestBody requestBody = new DatabaseRequestBody(
      newBooking.getUserId(), 
      newBooking.getVehicleNumber(), 
      newBooking.getSubscriptionName(), 
      newBooking.getPickupPointId(), 
      newBooking.getDeliveredAt(),
      newBooking.getReturnedAt(),
      newBooking.getKilometerDriven());

    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
    /*try {
      Connection conn = databaseService.getConnection();
      String query = "INSERT INTO bookings (user_id," +
          "vehicle_number," +
          "subscription_name," +
          "pickup_point_name," +
          "delivered_at)" +
          "VALUES (?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);

      preparedStatement.setObject(1, newBooking.getUserId());
      preparedStatement.setObject(2, newBooking.getVehicleNumber());
      preparedStatement.setString(3, newBooking.getSubscriptionName());
      preparedStatement.setObject(4, newBooking.getPickupPointName());
      preparedStatement.setObject(5, newBooking.getDeliveredAt());


      preparedStatement.executeUpdate();

    } catch (SQLException e){
      e.printStackTrace();
    }*/
  }

  public int getAmountOfBookingsAtDate(LocalDateTime date){
    int amount = 0;
    String query = "SELECT * FROM bookings WHERE created_at BETWEEN ? AND ?";
    DatabaseRequestBody body = new DatabaseRequestBody(date, date.plusDays(1));
    DatabaseResponse databaseResponse = databaseService.executeQuery(query, body);
    while (databaseResponse.hasNext()){
        amount++;
    }
    return amount;
  }


  public List<Booking> getBookingList(User findByUser) {

    String query = "SELECT * FROM bookings "
      + "INNER JOIN subscriptions ON bookings.subscription_name=subscriptions.name "
      + "INNER JOIN cars ON bookings.vehicle_number=cars.vehicle_number "
      + "LEFT JOIN damage_reports ON bookings.id=damage_reports.booking_id";
    DatabaseResponse databaseResponse = databaseService.executeQuery(query, new DatabaseRequestBody());
    return parseResponse(databaseResponse);
    /*
    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    List<Booking> bookings = new ArrayList<>();
    try {
      Connection conn = databaseService.getConnection();
      String query = "SELECT * FROM bookings WHERE user_id = ?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setObject(1, findByUser.getId());
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        bookings.add(new Booking(
            resultSet.getLong("id"),
            resultSet.getLong("user_id"),
            resultSet.getLong("vehicle_number"),
            resultSet.getString("subscription_name"),
            resultSet.getString("pickup_point_name"),
            (LocalDateTime) resultSet.getObject("delivered_at"),
            (LocalDateTime) resultSet.getObject("created_at"),
            (LocalDateTime) resultSet.getObject("updated_at"))
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookings;*/
  }

  public Booking find(String column, Object value) {
	String sql = String.format(
		  "SELECT * FROM bookings "
		+ "INNER JOIN subscriptions ON bookings.subscription_name=subscriptions.name "
      	+ "INNER JOIN cars ON bookings.vehicle_number=cars.vehicle_number "
      	+ "LEFT JOIN damage_reports ON bookings.id=damage_reports.booking_id "
		+ "WHERE %s=?", column);
    DatabaseRequestBody body = new DatabaseRequestBody(value);
    DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
    return parseResponseFirst(databaseResponse);
}

  public Booking findByBookingId(Long id) {

    String sql = "SELECT * FROM bookings WHERE id = ?";
    DatabaseRequestBody requestBody = new DatabaseRequestBody(id);
    DatabaseResponse databaseResponse = databaseService.executeQuery(sql, requestBody);

    return parseResponseFirst(databaseResponse);

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
/*
    try {
      Connection conn = databaseService.getConnection();
      String query = "SELECT * FROM bookings WHERE id=?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setLong(1, findById);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        findBooking = new Booking(
            resultSet.getLong("id"),
            resultSet.getLong("user_id"),
            resultSet.getLong("vehicle_number"),
            resultSet.getString("subscription_name"),
            resultSet.getString("pickup_point_name"),
            (LocalDateTime) resultSet.getObject("delivered_at"),
            (LocalDateTime) resultSet.getObject("created_at"),
            (LocalDateTime) resultSet.getObject("updated_at"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return findBooking;*/
  }

  public boolean updateBooking(Booking booking) {

    String query = "UPDATE bookings SET user_id = ?, vehicle_number = ?, subscription_name = ?, pickup_point_id = ?, delivered_at = ?, returned_at = ?, updated_at = ?, kilometer_driven = ? WHERE id=?";

    DatabaseRequestBody requestBody = new DatabaseRequestBody(booking.getUserId(), booking.getVehicleNumber(),
        booking.getSubscriptionName(), booking.getPickupPointId(), booking.getDeliveredAt(), booking.getReturnedAt(), booking.getUpdatedAt(), booking.getKilometerDriven(), booking.getId());
    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
  }

  public boolean setDeliveredAt(long id) {
    String query = "UPDATE bookings SET delivered_at = ? WHERE id=?";
    DatabaseRequestBody requestBody = new DatabaseRequestBody(LocalDateTime.now(), id);
    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
  }

  public boolean setReturnedAt(long id) {
    String query = "UPDATE bookings SET returned_at = ? WHERE id=?";
    DatabaseRequestBody requestBody = new DatabaseRequestBody(LocalDateTime.now(), id);
    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
  }

  public boolean delete(Booking booking) {
    String query = String.format("DELETE FROM bookings WHERE id = ?");
    DatabaseRequestBody requestBody = new DatabaseRequestBody(booking.getId());
    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
  }

  public Booking last() {
      String sql = "SELECT * FROM bookings ORDER BY created_at DESC LIMIT 1";
      DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
      return parseResponseFirst(databaseResponse);
  }

  public Booking parseResponseFirst(DatabaseResponse databaseResponse) {
    List<Booking> bookings = parseResponse(databaseResponse);
    if (bookings.size() == 0) return null;
    else return bookings.get(0);
}

  private List<Booking> parseResponse(DatabaseResponse databaseResponse) {
		List<Booking> bookings = new LinkedList<Booking>();
		while (databaseResponse.hasNext()) {
		DatabaseRecord record = databaseResponse.next();

		Booking booking = new Booking(
			(long) record.map().get("id"),
			(long) record.map().get("user_id"),
			(long) record.map().get("vehicle_number"),
			(String) record.map().get("subscription_name"),
			(long) record.map().get("pickup_point_id"),
			(LocalDateTime) record.map().get("delivered_at"),
			(LocalDateTime) record.map().get("created_at"),
			(LocalDateTime) record.map().get("updated_at"),
			(LocalDateTime) record.map().get("returned_at"),
			(double) record.map().get("kilometer_driven")
		);
		
		if (record.map().get("name") != null) {
			Subscription subscription = new Subscription(
				(String) record.map().get("name"),
				(double) record.map().get("days"),
				(double) record.map().get("price"),
				(Boolean) record.map().get("available")
			);
			booking.setSubscription(subscription);
		}

		if (record.map().get("frame_number") != null) {
			Car car = new Car(
				(String) record.map().get("frame_number"), 
				(String) record.map().get("brand"), 
				(String) record.map().get("model"), 
				(String) record.map().get("color"), 
				(int) record.map().get("equipment_level"), 
				(double) record.map().get("steel_price"), 
				(double) record.map().get("registration_fee"), 
				(double) record.map().get("co2_discharge"), 
				(Boolean) record.map().get("inspected"), 
				(Boolean) record.map().get("damaged")
			);
			booking.setCar(car);
		}

		if (record.map().get("booking_id") != null) {
			DamageReport damageReport = new DamageReport((long) record.map().get("booking_id"));
			booking.setDamageReport(damageReport);
		}

		bookings.add(booking);
    }

    return bookings;
  }

}
