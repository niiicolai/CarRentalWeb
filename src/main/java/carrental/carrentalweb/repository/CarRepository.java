package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.services.DatabaseService;
import groovyjarjarantlr4.v4.parse.ANTLRParser.elementEntry_return;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Written by Mikkel Aabo Simonsen
 */
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
                    "inspected," +
                    "damaged," +
                    "sold," +
                    "sell_price)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setBoolean(10, false);
            preparedStatement.setBoolean(11, false);
            preparedStatement.setDouble(12, newCar.getSellPrice());
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

            cars = parseCars(resultSet);

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

            foundCar = parseCar(resultSet);
            
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
                    "updated_at=?," +
                    "damaged=? " +
                    "sold=?" +
                    "sell_price=?" +
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
            preparedStatement.setBoolean(10, car.getDamaged());
            preparedStatement.setBoolean(11, car.getSold());
            preparedStatement.setDouble(12, car.getSellPrice());
            preparedStatement.setLong(13, car.getVehicleNumber());

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sellCar(long vehicleNumber){
        String query = "UPDATE cars SET sold=1 WHERE vehicle_number=?";
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, vehicleNumber);

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

			foundCar = parseCar(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundCar;
    }

    // Returns all cars available for rent
    public List<Car> getCarsAvailableForRent() {
        return getCarsAvailableForRent(0);
    }

    // Returns all cars available for rent with a limit
    public List<Car> getCarsAvailableForRent(int limit) {
        List<Car> cars = new ArrayList<>();

		try {
			Connection conn = databaseService.getConnection();
            String sql = "SELECT * FROM cars "
                       + "WHERE sold = 0 AND inspected = 1 AND damaged = 0 "
                       + "ORDER BY created_at DESC";

            if (limit > 0) sql += String.format(" LIMIT %d", limit);
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
            
            List<Car> availableCars = parseCars(resultSet);
            List<Car> carsWithOpenBookings = getCarsWithOpenBookings();
            
            for (int i = 0; i < availableCars.size(); i++) {
                Car availableCar = availableCars.get(i);
                
                boolean hasBooking = false;
                for (int j = 0; j < carsWithOpenBookings.size(); j++) {
                    if (availableCar.getVehicleNumber() == carsWithOpenBookings.get(j).getVehicleNumber()) {
                        hasBooking = true;
                        break;
                    }
                }

                if (!hasBooking) {
                    cars.add(availableCar);
                }
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cars;
    }

    public List<Car> getCarsWithOpenBookings() {
        List<Car> cars = new ArrayList<>();

		try {
			Connection conn = databaseService.getConnection();
            String carsWithOpenBookings = "SELECT * FROM carrental.cars "
                        + "INNER JOIN bookings ON cars.vehicle_number = bookings.vehicle_number "
                        + "LEFT JOIN damage_reports ON bookings.id = damage_reports.booking_id "
                        + "WHERE cars.sold = 0 AND cars.inspected = 1 AND cars.damaged = 0 "
                        + "AND damage_reports.booking_id IS NULL "
                        + "OR cars.sold = 0 AND cars.inspected = 1 AND cars.damaged = 0 "
                        + "AND bookings.returned_at IS NULL "
                        + "ORDER BY cars.created_at DESC";
			
			PreparedStatement preparedStatement = conn.prepareStatement(carsWithOpenBookings);
            System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
            cars = parseCars(resultSet);
            
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cars;
    }

    public boolean isCarAvailableForRent(long vehicleNumber) {
        Car car = findCarByVehicleNumber(vehicleNumber);        
        if (car.getSold() || car.getDamaged() || !car.isInspected()) {
            return false;
        }

        List<Car> carsWithOpenBookings = getCarsWithOpenBookings();
        for (int i = 0; i < carsWithOpenBookings.size(); i++) {
            if (carsWithOpenBookings.get(i).getVehicleNumber() == vehicleNumber) {
                return false;
            }
        }

        return true;
    }

    // Har tilføjet de her to metoder til enten at parse
    // en enkel bil fra et resultSet, eller en liste af biler.
    // På grund af hver eneste gang man ændre antallet af attributter
    // forventet fra databasen eller tilføjer en ny get metode,
    // så skal man duplicate 20+ linjer i x+ metoder, hvilket både
    // fører til fejl og giver et masse ekstra arbejde.
    // ~ Nicolai (parseCar, parseCars)
    //
    // Note: Jeg har ikke ændret på hvordan dine kode fungere,
    // jeg har bare flyttet de 20+ linjer der var duplikeret,
    // ned i to metoder.
    public Car parseCar(ResultSet resultSet) throws SQLException {
        List<Car> cars = parseCars(resultSet);
        if (cars.size() == 0)
            return null;
        else
            return cars.get(0);
    }

    public List<Car> parseCars(ResultSet resultSet) throws SQLException {
        List<Car> cars = new ArrayList<>();

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
            boolean damaged = resultSet.getBoolean("damaged");
            boolean sold = resultSet.getBoolean("sold");
            double sellPrice = resultSet.getDouble("sell_price");
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
                    damaged,
                    sold,
                    sellPrice,
                    createdAt,
                    updatedAt));
        }

        return cars;
    }
}