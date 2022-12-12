package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entity_factories.TestCarFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.services.DatabaseService;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {
    private static Car testCar;

    private static Car lastInsertedCar;

    private static CarRepository carRepository;


    @BeforeAll
    public static void init(DatabaseService databaseService){

        carRepository = new CarRepository(databaseService);
    }

    @Test
    @Order(1)
    public void testInsertAndLast_SaveToDatabase_AndReturnDataBaseObject(){
        testCar = TestCarFactory.create(true);

        //Act
        carRepository.createCar(testCar);
        lastInsertedCar = carRepository.last();

        //Assert
        assertNotEquals(0, lastInsertedCar.getVehicleNumber(), "Last inserted vehicle number must never be 0");
        assertEquals(testCar.getFrameNumber(), lastInsertedCar.getFrameNumber(), "Frame numbers must be equal");
        assertEquals(testCar.getBrand(), lastInsertedCar.getBrand(), "Brand must be equal");
        assertEquals(testCar.getModel(), lastInsertedCar.getModel(), "Model must be equal");
        assertEquals(testCar.getColor(), lastInsertedCar.getColor(), "Color must be equal");
        assertEquals(testCar.getEquipmentLevel(), lastInsertedCar.getEquipmentLevel(), "equipmentLevel must be equal");
        assertEquals(testCar.getSteelPrice(), lastInsertedCar.getSteelPrice(), "steelPrice must be equal");
        assertEquals(testCar.getRegistrationFee(), lastInsertedCar.getRegistrationFee(), "registrationFee");
        assertEquals(testCar.getCo2Discharge(), lastInsertedCar.getCo2Discharge(), "co2Discharge must be equal");
        assertEquals(testCar.getInspected(), lastInsertedCar.getInspected(), "inspected must be equal");
    }

    @Test
    @Order(2)

    public void testFind_ReturnsUserObjectFromDatabase() {
        // Act
        Car foundCar = carRepository.findCarByVehicleNumber(lastInsertedCar.getVehicleNumber());

        // Assert
        assertEquals(foundCar.getFrameNumber(), lastInsertedCar.getFrameNumber(), "Frame numbers must be equal");
        assertEquals(foundCar.getBrand(), lastInsertedCar.getBrand(), "Brand must be equal");
        assertEquals(foundCar.getModel(), lastInsertedCar.getModel(), "Model must be equal");
        assertEquals(foundCar.getColor(), lastInsertedCar.getColor(), "Color must be equal");
        assertEquals(foundCar.getEquipmentLevel(), lastInsertedCar.getEquipmentLevel(), "equipmentLevel must be equal");
        assertEquals(foundCar.getSteelPrice(), lastInsertedCar.getSteelPrice(), "steelPrice must be equal");
        assertEquals(foundCar.getRegistrationFee(), lastInsertedCar.getRegistrationFee(), "registrationFee");
        assertEquals(foundCar.getCo2Discharge(), lastInsertedCar.getCo2Discharge(), "co2Discharge must be equal");
        assertEquals(foundCar.getInspected(), lastInsertedCar.getInspected(), "inspected must be equal");
    }


    @Test
    @Order(3)
    public void testUpdate_SavesToDatabase() {
        // Arrange
        Car modifiedCar = TestCarFactory.create(!lastInsertedCar.getInspected());
        modifiedCar.setVehicleNumber(lastInsertedCar.getVehicleNumber());

        // Act
        carRepository.updateCar(modifiedCar);
        Car updatedCar = carRepository.findCarByVehicleNumber(lastInsertedCar.getVehicleNumber());

        //Assert
        assertEquals(lastInsertedCar.getVehicleNumber(), updatedCar.getVehicleNumber());
        assertNotEquals(lastInsertedCar.getBrand(), updatedCar.getBrand(), "Brand must not be equal");
        assertNotEquals(lastInsertedCar.getModel(), updatedCar.getModel(), "Model must not be equal");
        assertNotEquals(lastInsertedCar.getColor(), updatedCar.getColor(), "Color must not be equal");
        assertNotEquals(lastInsertedCar.getEquipmentLevel(), updatedCar.getEquipmentLevel(), "equipmentLevel must not be equal");
        assertNotEquals(lastInsertedCar.getSteelPrice(), updatedCar.getSteelPrice(), "steelPrice must be not equal");
        assertNotEquals(lastInsertedCar.getRegistrationFee(), updatedCar.getRegistrationFee(), "registrationFee must not be equal");
        assertNotEquals(lastInsertedCar.getCo2Discharge(), updatedCar.getCo2Discharge(), "co2Discharge must be equal");
        assertNotEquals(lastInsertedCar.getInspected(), updatedCar.getInspected(), "inspected must not be equal");

    }
    @Test
    @Order(4)
    public void testDelete_SavesToDatabase() {
        // Act
        carRepository.deleteCarByVehicleNumber(lastInsertedCar.getVehicleNumber());
        Car car = carRepository.findCarByVehicleNumber(lastInsertedCar.getVehicleNumber());

        // Assert
        assertNull(car, "Car must be null");
    }
}
