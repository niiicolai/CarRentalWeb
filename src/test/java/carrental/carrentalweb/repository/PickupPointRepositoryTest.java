package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.entity_factories.TestPickupPointFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.services.DatabaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;






@SpringBootTest
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)




class PickupPointRepositoryTest {

  private static PickupPoint testPickupPoint;


  private static PickupPoint lastInsertedPickupPoint;
  private static User lastInsertedUser;


  private static BookingRepository bookingRepository;
  private static SubscriptionRepository subscriptionRepository;
  private static CarRepository carRepository;
  private static PickupPointRepository pickupPointRepository;
  private static UserRepository userRepository;



  /*
   * The database service connected to
   * a seperate test database version of the
   * original database.
   */
  private static DatabaseService database;

  /*
   * Executed before all tests.
   * Caches the injected database service, and
   * creates an instance of the user repository.
   *
   * It also contains the arrange part
   * of several tests' AAA pattern.
   */

  @BeforeAll
  public static void init(DatabaseService databaseService) {
    database = databaseService;

    // Arrange
    pickupPointRepository = new PickupPointRepository(database);
    Address address = new Address("Gade", "Valby", "2500", "Danmark", LocalDateTime.now(), LocalDateTime.now());

    // Create test pickup
    pickupPointRepository.createPickupPoint(TestPickupPointFactory.create(1));
    lastInsertedPickupPoint = pickupPointRepository.last();
    //lastInsertedPickupPoint.setAddressId();
  }

  @AfterAll
  public static void after() {

    pickupPointRepository.delete(lastInsertedPickupPoint);

  }


    @Test
    @Order(1)
    void createPickupPoint() {
         // Arrange
        testPickupPoint = TestPickupPointFactory.create(lastInsertedPickupPoint.getId());

      // Act
      pickupPointRepository.createPickupPoint(testPickupPoint);
      lastInsertedPickupPoint = pickupPointRepository.last();

      // Assert
      assertEquals(0L, lastInsertedPickupPoint.getId(), "Id must not be 0");

    }
/*
    @Test
    void getPickupPointsList() {
    }

    @Test
    void findPickupPointById() {
    }

    @Test
    void updatePickupPoint() {
    }

    @Test
    void delete() {
    }

    @Test
    void last() {
    }


    /*
       * A user used created by the test user factory.
       * Its id is therefore equal to 0.
       * 'lastInsertedUser' is the database version
       * of the 'testUser' variable.
       *
       * It is different from the database version
       * because it is used to test against the
       * values of the database version.
       /
      private static PickupPoint testPickupPoint;

      /
       * The database version of the
       * 'testPoint' variable.
       /
      private static PickupPoint lastInsertedPoint;

      /
       * The repository that is being tested.
       /
      private static PickupPointRepository repository;

      /
       * The database service connected to
       * a seperate test database version of the
       * original database.
       */
}