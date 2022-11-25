package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Address;
import carrental.carrentalweb.entities.PickupPoint;
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
public class PickupPointRepository {

  private final DatabaseService databaseService;

  public PickupPointRepository(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  public void createPickupPoint(PickupPoint newPickupPoint) {

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
      Connection conn = databaseService.getConnection();
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
      Connection conn = databaseService.getConnection();
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
      Connection conn = databaseService.getConnection();
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


  public PickupPoint findPickupPointByZipcode(String findZipcode){

    PickupPoint pickupPointZip = new PickupPoint();

    for (PickupPoint pickupPoint: getPickupPointsList()){
      if (pickupPoint.getAddress().getZipCode().equals(findZipcode)){
      pickupPointZip = pickupPoint;
      }
    }
    return pickupPointZip;
  }

  public void updatePickupPoint (PickupPoint pickupPoint){

    //Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

    try {
      Connection conn = databaseService.getConnection();
      String query = "UPDATE bookings SET address = ?, created_at = ?, updated_at = ? WHERE location_name = ?";

      PreparedStatement preparedStatement = conn.prepareStatement(query);

      preparedStatement.setObject(1, pickupPoint.getAddress());
      preparedStatement.setObject(2, pickupPoint.getCreatedAt());
      preparedStatement.setObject(3, pickupPoint.getUpdatedAt());
      preparedStatement.setString(4, pickupPoint.getName());

      preparedStatement.executeUpdate();
    } catch (SQLException e){
      e.printStackTrace();
    }
  }
}
