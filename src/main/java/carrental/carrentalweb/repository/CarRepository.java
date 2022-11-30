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
                    "(frame_number," +
                    "brand," +
                    "model," +
                    "color," +
                    "equipment_level," +
                    "steel_price," +
                    "registration_fee," +
                    "co2_discharge," +
                    "inspected) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println("Created query");
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            System.out.println("Created preparedStatement");

            preparedStatement.setString(1, newCar.getFrameNumber());
            preparedStatement.setString(2, newCar.getBrand());
            preparedStatement.setString(3, newCar.getModel());
            preparedStatement.setString(4, newCar.getColor());
            preparedStatement.setInt(5, newCar.getEquipmentLevel());
            preparedStatement.setDouble(6, newCar.getSteelPrice());
            preparedStatement.setDouble(7, newCar.getRegistrationFee());
            preparedStatement.setDouble(8, newCar.getCo2Discharge());
            preparedStatement.setBoolean(9, newCar.getInspected());
            System.out.println(preparedStatement);


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
                long vehicleNumber = resultSet.getLong("vehicle_number");
                String frameNumber = resultSet.getString("frame_number");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int equipmentLevel = resultSet.getInt("equipment_level");
                double steelPrice = resultSet.getDouble("steel_price");
                double registrationFee = resultSet.getDouble("registration_fee");
                double co2Discharge = resultSet.getDouble("co2_discharge");
                boolean inspected = resultSet.getBoolean("inspected");
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");
                cars.add(new Car(vehicleNumber,
                        frameNumber,
                        brand,
                        model, color,
                        equipmentLevel,
                        steelPrice,
                        registrationFee,
                        co2Discharge,
                        inspected,
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
                long vehicleNumber = resultSet.getLong("vehicle_number");
                String frameNumber = resultSet.getString("frame_number");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int equipmentLevel = resultSet.getInt("equipment_level");
                double steelPrice = resultSet.getDouble("steel_price");
                double registrationFee = resultSet.getDouble("registration_fee");
                double co2Discharge = resultSet.getDouble("co2_discharge");
                boolean inspected = resultSet.getBoolean("inspected");
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");
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
                    "model=?," +
                    "brand=?," +
                    "updated_at=?" +
                    "WHERE vehicle_number=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, car.getColor());
            preparedStatement.setInt(2, car.getEquipmentLevel());
            preparedStatement.setDouble(3, car.getSteelPrice());
            preparedStatement.setDouble(4, car.getRegistrationFee());
            preparedStatement.setDouble(5, car.getCo2Discharge());
            preparedStatement.setBoolean(6, car.getInspected());
            preparedStatement.setString(7, car.getModel());
            preparedStatement.setString(8, car.getBrand());
            preparedStatement.setObject(9, LocalDateTime.now());
            preparedStatement.setLong(10, car.getVehicleNumber());

            System.out.println(preparedStatement);

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

    public Car last() {
		Car foundCar = null;

		try {
			Connection conn = databaseService.getConnection();
			String query = "SELECT * FROM cars ORDER BY created_at DESC LIMIT 1";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()){
                long vehicleNumber = resultSet.getLong("vehicle_number");
                String frameNumber = resultSet.getString("frame_number");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int equipmentLevel = resultSet.getInt("equipment_level");
                double steelPrice = resultSet.getDouble("steel_price");
                double registrationFee = resultSet.getDouble("registration_fee");
                double co2Discharge = resultSet.getDouble("co2_discharge");
                boolean inspected = resultSet.getBoolean("inspected");
                LocalDateTime createdAt = (LocalDateTime) resultSet.getObject("created_at");
                LocalDateTime updatedAt = (LocalDateTime) resultSet.getObject("updated_at");
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
                        createdAt,
                        updatedAt);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundCar;
    }
}