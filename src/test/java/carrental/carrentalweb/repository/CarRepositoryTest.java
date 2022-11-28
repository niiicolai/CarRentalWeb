package carrental.carrentalweb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entity_factories.TestCarFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {
    private static Car testCar;

    private static Car lastInsertedCar;

    private static CarRepository carRepository;

    private static DatabaseService database;



    @BeforeAll
    public static void init(DatabaseService databaseService){
        database = databaseService;

        carRepository = new CarRepository(databaseService);
    }

    @Test
    @Order(1)
    public void testInsertAndLast_SaveToDatabase_AndReturnDataBaseObject(){
        testCar = TestCarFactory.create(testCar.getInspected());

        //Act
        carRepository.createCar(testCar);
        lastInsertedCar = carRepository.last();

        //Assert
        assertNotEquals(0, lastInsertedCar.getVehicleNumber(), "Last inserted vehicle number must never be 0");
        assertEquals(testCar.getFrameNumber(), lastInsertedCar.getFrameNumber(), "Frame numbers must be equal");
        assertEquals(testCar.getBrand(), lastInsertedCar.getBrand(), "Brand must be equal");
        assertEquals(testCar.getModel(), lastInsertedCar.getModel(), "Model must be equal");
    }
}
