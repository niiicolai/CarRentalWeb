package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.services.DatabaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {

  @Autowired
  DatabaseService databaseService;

  public void createBooking(Booking newBooking) {

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
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
    }
  }


  public List<Booking> getBookingList(User findByUser) {

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    List<Booking> bookings = new ArrayList<>();
    try {
      Connection conn = databaseService.getConnection();
      String query = "SELECT * FROM bookings WHERE user = ?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setObject(1, findByUser);
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
    return bookings;
  }


  public Booking findBookingId(Long findById){

    Booking findBooking = new Booking();

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

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
    } catch(SQLException e){
      e.printStackTrace();
    }
    return findBooking;
  }

  public void updateBooking(Booking booking){
    try {
      Connection conn = databaseService.getConnection();
      String queryCreate = "UPDATE bookings " +
          "SET user_id = ?, vehicle_number = ?, subscription_name =?, pickup_point_name = ?, delivered_at = ? WHERE id=?";
      PreparedStatement preparedStatement = conn.prepareStatement(queryCreate);

      preparedStatement.setObject(1, booking.getUserId());
      preparedStatement.setObject(2, booking.getVehicleNumber());
      preparedStatement.setString(3, booking.getSubscriptionName());
      preparedStatement.setObject(4, booking.getPickupPointName());
      preparedStatement.setObject(5, booking.getDeliveredAt());
      preparedStatement.setLong(6, booking.getId());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Booking last() {
	return null;
  }

}
