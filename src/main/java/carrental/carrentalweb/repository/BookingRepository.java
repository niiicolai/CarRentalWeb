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
      String query = "INSERT INTO bookings (car," +
          "subscription_name," +
          "creditrating," +
          "pickup_point," +
          "damage_report," +
          "delivered_at" +
          "created_at" +
          "updated_at)" +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);

      preparedStatement.setObject(1, newBooking.getUser());
      preparedStatement.setObject(2, newBooking.getCar());
      preparedStatement.setString(3, newBooking.getSubscriptionName());
      preparedStatement.setObject(4, newBooking.getCreditRating());
      preparedStatement.setObject(5, newBooking.getPickupPoint());
      preparedStatement.setObject(6, newBooking.getDamageReport());
      preparedStatement.setObject(7, newBooking.getDeliveredAt());
      preparedStatement.setObject(8, newBooking.getCreatedAt());
      preparedStatement.setObject(9, newBooking.getUpdatedAt());


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
        Long id = resultSet.getLong(1);
        User user = (User) resultSet.getObject(2);
        Car car = (Car) resultSet.getObject(3);
        String subName= resultSet.getString(4);
        CreditRating cr = (CreditRating) resultSet.getObject(5);
        PickupPoint pp = (PickupPoint) resultSet.getObject(6);
        DamageReport dr = (DamageReport) resultSet.getObject(7);
        LocalDateTime deliveredAt =  (LocalDateTime) resultSet.getObject(8);
        LocalDateTime createdAt =  (LocalDateTime) resultSet.getObject(9);
        LocalDateTime updatedAt =  (LocalDateTime) resultSet.getObject(10);

        bookings.add(new Booking(id,
            user,
            car,
            subName,
            cr,
            pp,
            dr,
            deliveredAt,
            createdAt,
            updatedAt));
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
        Long id = resultSet.getLong(1);
        User user = (User) resultSet.getObject(2);
        Car car = (Car) resultSet.getObject(3);
        String subName= resultSet.getString(4);
        CreditRating cr = (CreditRating) resultSet.getObject(5);
        PickupPoint pp = (PickupPoint) resultSet.getObject(6);
        DamageReport dr = (DamageReport) resultSet.getObject(7);
        LocalDateTime deliveredAt =  (LocalDateTime) resultSet.getObject(8);
        LocalDateTime createdAt =  (LocalDateTime) resultSet.getObject(9);
        LocalDateTime updatedAt =  (LocalDateTime) resultSet.getObject(10);

        findBooking = new Booking(id,
            user,
            car,
            subName,
            cr,
            pp,
            dr,
            deliveredAt,
            createdAt,
            updatedAt);
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
          "SET userid = ?, car = ?, subscription_name =?, credit_rating = ?, pickup_point = ?, damage_report = ?, delivered_at = ?, created_at = ?, updated_at = ? WHERE id=?";
      PreparedStatement preparedStatement = conn.prepareStatement(queryCreate);


      preparedStatement.setObject(1, booking.getUser());
      preparedStatement.setObject(2, booking.getCar());
      preparedStatement.setString(3, booking.getSubscriptionName());
      preparedStatement.setObject(4, booking.getCreditRating());
      preparedStatement.setObject(5, booking.getPickupPoint());
      preparedStatement.setObject(6, booking.getDamageReport());
      preparedStatement.setObject(7, booking.getDeliveredAt());
      preparedStatement.setObject(8, booking.getCreatedAt());
      preparedStatement.setObject(9, booking.getUpdatedAt());
      preparedStatement.setLong(10, booking.getId());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
