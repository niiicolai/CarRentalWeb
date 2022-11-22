package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.utilities.MySQLConnector;
import org.springframework.beans.factory.annotation.Value;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PickupPointRepository {
  @Value("${JDBCUrl}")
  private String url;
  @Value("${JDBCUsername")
  private String username;
  @Value("${JDBCPassword")
  private String password;

  private Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

  public void createPickupPoint(PickupPoint newPickupPoint) {

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
      String query = "INSERT INTO pickup_points (location_name," +
          "address," +
          "created_at," +
          "updated_at)" +
          "VALUES (?, ?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query);

      preparedStatement.setString(1, newPickupPoint.getName());
      preparedStatement.setObject(2, newPickupPoint.getAddress());
      preparedStatement.setObject(3, newPickupPoint.getCreatedAt());

      preparedStatement.executeUpdate();

    } catch (SQLException e){
      e.printStackTrace();
    }
  }


  public List<PickupPoint> getPickupPointsList() {

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    List<PickupPoint> pickupPoints = new ArrayList<>();
    try {
      String query = "SELECT * FROM pickup_points";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString(1);
        Address address = (Address) resultSet.getObject(2);
        LocalDateTime createdAt =  (LocalDateTime) resultSet.getObject(3);
        LocalDateTime updatedAt =  (LocalDateTime) resultSet.getObject(4);

        pickupPoints.add(new PickupPoint(name,
            address,
            createdAt,
            updatedAt));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pickupPoints;
  }


  public PickupPoint findPickupPointByName(String findName){

    PickupPoint pickupPoint = new PickupPoint();

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
      String query = "SELECT * FROM pickup_points WHERE location_name=?";
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setString(1, findName);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()){
        String name = resultSet.getString(1);
        Address address = (Address) resultSet.getObject(2);
        LocalDateTime createdAt =  (LocalDateTime) resultSet.getObject(3);
        LocalDateTime updatedAt =  (LocalDateTime) resultSet.getObject(4);

       pickupPoint = new PickupPoint(name,
            address,
            createdAt,
            updatedAt);
      }
    } catch(SQLException e){
      e.printStackTrace();
    }
    return pickupPoint;
  }

  public void updatePickupPoint (PickupPoint pickupPoint){

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
      String query = "INSERT INTO pickup_points (location_name," +
          "address," +
          "created_at," +
          "updated_at)" +
          "VALUES (?, ?, ?, ?)" +
          "WHERE location_name = ?";

      PreparedStatement preparedStatement = conn.prepareStatement(query);

      preparedStatement.setString(1, pickupPoint.getName());
      preparedStatement.setObject(2, pickupPoint.getAddress());
      preparedStatement.setObject(3, pickupPoint.getCreatedAt());
      preparedStatement.setObject(4, pickupPoint.getUpdatedAt());
      preparedStatement.setString(5, pickupPoint.getName());

      preparedStatement.executeUpdate();
    } catch (SQLException e){
      e.printStackTrace();
    }
  }
}
