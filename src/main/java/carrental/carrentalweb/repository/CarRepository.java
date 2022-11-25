package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.services.DatabaseService;

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

    private final DatabaseService databaseService;

    public CarRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Car createCar(Car newCar) {

        try {
            Connection conn = databaseService.getConnection();
            String query = "INSERT INTO cars " +
                    "(vehicle_number," +
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
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println("Created query");
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            System.out.println("Created preparedStatement");

            preparedStatement.setLong(1, newCar.getVehicleNumber());
            preparedStatement.setString(2, newCar.getFrameNumber());
            preparedStatement.setString(3, newCar.getBrand());
            preparedStatement.setString(4, newCar.getModel());
            preparedStatement.setString(5, newCar.getColor());
            preparedStatement.setInt(6, newCar.getEquipmentLevel());
            preparedStatement.setDouble(7, newCar.getSteelPrice());
            preparedStatement.setDouble(8, newCar.getRegistrationFee());
            preparedStatement.setDouble(9, newCar.getCo2Discharge());
            preparedStatement.setBoolean(10, newCar.getInspected());
            preparedStatement.setObject(11, newCar.getBooking());
            preparedStatement.setObject(12, LocalDateTime.now());
            preparedStatement.setObject(13, LocalDateTime.now());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return newCar;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            Connection conn = databaseService.getConnection();
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
        try {
            Connection conn = databaseService.getConnection();
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
        try {
            Connection conn = databaseService.getConnection();
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
        String query = "DELETE FROM cars WHERE vehicle_number=?";
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, vehicleNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}