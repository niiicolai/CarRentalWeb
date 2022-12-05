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

    String query = "INSERT INTO bookings (user_id, vehicle_number, subscription_name, pickup_point_id, delivered_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

    DatabaseRequestBody requestBody = new DatabaseRequestBody(
      newBooking.getUserId(), 
      newBooking.getVehicleNumber(), 
      newBooking.getSubscriptionName(), 
      newBooking.getPickupPointId(), 
      newBooking.getDeliveredAt(),
        LocalDateTime.now(),
        LocalDateTime.now());

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


  public List<Booking> getBookingList(User findByUser) {

    String query = "SELECT * FROM bookings";
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
    String sql = String.format("SELECT * FROM bookings WHERE %s=?", column);
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

    String query = "UPDATE bookings SET user_id = ?, vehicle_number = ?, subscription_name = ?, pickup_point_id = ?, delivered_at = ?, created_at = ?, updated_at = ? WHERE id=?";

    DatabaseRequestBody requestBody = new DatabaseRequestBody(booking.getUserId(), booking.getVehicleNumber(),
        booking.getSubscriptionName(), booking.getPickupPointId(), booking.getDeliveredAt(), booking.getCreatedAt(), LocalDateTime.now(), booking.getId());
    DatabaseResponse databaseResponse = databaseService.executeUpdate(query, requestBody);
    return databaseResponse.isSuccessful();
/*
    try {
      Connection conn = databaseService.getConnection();
      String queryCreate = "UPDATE bookings " +
          "SET user_id = ?, vehicle_number = ?, subscription_name =?, pickup_point_name = ?, delivered_at = ?, created_at = ?, updated_at = ? WHERE id=?";
      PreparedStatement preparedStatement = conn.prepareStatement(queryCreate);

      preparedStatement.setObject(1, booking.getUserId());
      preparedStatement.setObject(2, booking.getVehicleNumber());
      preparedStatement.setString(3, booking.getSubscriptionName());
      preparedStatement.setObject(4, booking.getPickupPointName());
      preparedStatement.setObject(5, booking.getDeliveredAt());
      preparedStatement.setObject(5, booking.getCreatedAt());
      preparedStatement.setObject(5, LocalDateTime.now());
      preparedStatement.setLong(6, booking.getId());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }

 */
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

      bookings.add(
          new Booking(
            (long) record.map().get("id"),
            (long) record.map().get("user_id"),
            (long) record.map().get("vehicle_number"),
            (String) record.map().get("subscription_name"),
            (long) record.map().get("pickup_point_id"),
            (LocalDateTime) record.map().get("delivered_at"),
            (LocalDateTime) record.map().get("created_at"),
            (LocalDateTime) record.map().get("updated_at")

          )
      );
    }

    return bookings;
  }

}
