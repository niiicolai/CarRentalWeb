package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.utilities.MySQLConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public Car createCar(Car newCar) {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);

        try {
            String query = "INSERT INTO cars (vehicle_number," +
                    "frame_number," +
                    "brand," +
                    "model," +
                    "color," +
                    "equipment_level," +
                    "steel_price," +
                    "registration_fee," +
                    "co2_discharge," +
                    "inspected," +
                    "booking, " +
                    "created_at," +
                    "updated_at)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, newCar.getFrameNumber());
            preparedStatement.setString(2, newCar.getBrand());
            preparedStatement.setString(3, newCar.getModel());
            preparedStatement.setString(4, newCar.getColor());
            preparedStatement.setInt(5, newCar.getEquipmentLevel());
            preparedStatement.setDouble(6, newCar.getSteelPrice());
            preparedStatement.setDouble(7, newCar.getRegistrationFee());
            preparedStatement.setDouble(8, newCar.getCo2Discharge());
            preparedStatement.setBoolean(9, newCar.getInspected());
            preparedStatement.setObject(10, newCar.getBooking());
            preparedStatement.setObject(11, LocalDateTime.now());
            preparedStatement.setObject(12, LocalDateTime.now());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return newCar;
    }

    public List<Car> getAllCars() {
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        List<Car> cars = new ArrayList<>();
        try {
            String query = "SELECT * FROM cars";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long vehicleNumber = resultSet.getLong(1);
                String frameNumber = resultSet.getString(2);
                String brandNumber = resultSet.getString(3);
                String model = resultSet.getString(4);
                String color = resultSet.getString(5);
                int equipmentLevel = resultSet.getInt(6);
                double steelPrice = resultSet.getDouble(7);
                double registrationFee = resultSet.getDouble(8);
                double co2Discharge = resultSet.getDouble(9);
                boolean inspected = resultSet.getBoolean(10);
                Booking booking = (Booking) resultSet.getObject(11);
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject(12);
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject(13);
                cars.add(new Car(vehicleNumber,
                        frameNumber,
                        brandNumber,
                        model, color,
                        equipmentLevel,
                        steelPrice,
                        registrationFee,
                        co2Discharge,
                        inspected,
                        booking,
                        createdAt,
                        updatedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car findCarByVehicleNumber(long vehicleNumberInput){
        Car foundCar = new Car();
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        try {
            String query = "SELECT * FROM cars WHERE vehicle_number=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, vehicleNumberInput);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                long vehicleNumber = resultSet.getLong(1);
                String frameNumber = resultSet.getString(2);
                String brand = resultSet.getString(3);
                String model = resultSet.getString(4);
                String color = resultSet.getString(5);
                int equipmentLevel = resultSet.getInt(6);
                double steelPrice = resultSet.getDouble(7);
                double registrationFee = resultSet.getDouble(8);
                double co2Discharge = resultSet.getDouble(9);
                boolean inspected = resultSet.getBoolean(11);
                Booking booking = (Booking) resultSet.getObject(10);
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject(12);
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject(13);
                foundCar = new Car(vehicleNumber,
                        frameNumber,
                        brand,
                        model,
                        color,
                        equipmentLevel,
                        steelPrice,
                        registrationFee,
                        co2Discharge,
                        inspected,
                        booking,
                        createdAt,
                        updatedAt);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return foundCar;
    }

    public void updateCar (Car car){
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        try {
            String query = "UPDATE cars " +
                    "SET color=?," +
                    "equipment_level=?," +
                    "steel_price=?," +
                    "registration_fee=?," +
                    "co2_discharge=?," +
                    "inspected=?," +
                    "booking=?," +
                    "updated_at=?" +
                    "WHERE vehicle_number=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, car.getColor());
            preparedStatement.setInt(2, car.getEquipmentLevel());
            preparedStatement.setDouble(3, car.getSteelPrice());
            preparedStatement.setDouble(4, car.getRegistrationFee());
            preparedStatement.setDouble(5, car.getCo2Discharge());
            preparedStatement.setBoolean(6, car.getInspected());
            preparedStatement.setObject(7, car.getBooking());
            preparedStatement.setObject(8, LocalDateTime.now());
            preparedStatement.setLong(9, car.getVehicleNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteCarByVehicleNumber(long vehicleNumber){
        Connection conn = MySQLConnector.getInstance().getConnection(url, username, password);
        String query = "DELETE FROM cars WHERE vehicle_number=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, vehicleNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}