package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.entity_factories.*;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.services.DatabaseService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingRepositoryTest {

  private static Booking lastInsertedBooking;
  private static Subscription lastInsertedSubscription;
  private static Car lastInsertedCar;
  private static PickupPoint lastInsertedPickupPoint;
  private static User lastInsertedUser;

  private static BookingRepository bookingRepository;
  private static SubscriptionRepository subscriptionRepository;
  private static CarRepository carRepository;
  private static PickupPointRepository pickupPointRepository;
  private static UserRepository userRepository;

  private static Booking testBooking;

  private static DatabaseService database;


  @BeforeAll
  public static void init(DatabaseService databaseService) {
    database = databaseService;

    // Arrange
    // Create test repositories
    subscriptionRepository = new SubscriptionRepository(database);
    carRepository = new CarRepository(database);
    pickupPointRepository = new PickupPointRepository(database);
    userRepository = new UserRepository(database);
    bookingRepository = new BookingRepository(database);

    // Create test car
    carRepository.createCar(TestCarFactory.create(true));
    lastInsertedCar = carRepository.last();

    // Create test pickup
    pickupPointRepository.createPickupPoint(TestPickupPointFactory.create(1));
    lastInsertedPickupPoint = pickupPointRepository.last();

    // Create test user
    userRepository.insert(TestUserFactory.create());
    lastInsertedUser = userRepository.last();

    // Create test subscription
    subscriptionRepository.create(TestSubscriptionFactory.create(true));
    lastInsertedSubscription = subscriptionRepository.last();


  }

  @AfterAll
  public static void after() {
    bookingRepository.delete(lastInsertedBooking);
    subscriptionRepository.delete(lastInsertedSubscription);
    pickupPointRepository.delete(lastInsertedPickupPoint);
    userRepository.delete(lastInsertedUser);
    carRepository.deleteCarByVehicleNumber(lastInsertedCar.getVehicleNumber());
  }

  @Test
  @Order(1)
  void testCreateAndLast_SaveToDatabase_AndReturnDataBaseObject() {
    testBooking = TestBookingFactory.create(lastInsertedPickupPoint.getId(), lastInsertedSubscription.getName(),
        lastInsertedUser.getId(), lastInsertedCar.getVehicleNumber());

    //act
    bookingRepository.createBooking(testBooking);
    lastInsertedBooking = bookingRepository.last();

    //assert
    assertNotEquals(0L, lastInsertedBooking.getId(), "Id must not be 0");
    assertEquals(testBooking.getUserId(), lastInsertedBooking.getUserId(), "UserID must match");
    assertEquals(testBooking.getVehicleNumber(), lastInsertedBooking.getVehicleNumber(), "VehicleNumber must match");
    assertEquals(testBooking.getPickupPointId(), lastInsertedBooking.getPickupPointId(), "PickupPointId must match");
    assertEquals(testBooking.getSubscriptionName(), lastInsertedBooking.getSubscriptionName(), "SubscriptionName must match");

  }

  @Test
  @Order(2)
  void testGetBookingListAndFindByBooking() {

    assertNotEquals(0, bookingRepository.getBookingList(lastInsertedUser).size(), "Size must nok be 0");
    assertNotEquals(0L, bookingRepository.findByBookingId(lastInsertedBooking.getId()), "Id must not be 0");
  }

  @Test
  @Order(3)
  void testUpdateBooking() {
    //arrange
    pickupPointRepository.createPickupPoint(TestPickupPointFactory.create(1));
    PickupPoint updatedPickup = pickupPointRepository.last();

    carRepository.createCar(TestCarFactory.create(true));
    Car updatedCar = carRepository.last();

    userRepository.insert(TestUserFactory.create());
    User updatedUser = userRepository.last();

    subscriptionRepository.create(TestSubscriptionFactory.create(true));
    Subscription updatedSubscription = subscriptionRepository.last();

    Booking updatedBooking = TestBookingFactory.create(updatedPickup.getId(), updatedSubscription.getName(), updatedUser.getId(), updatedCar.getVehicleNumber());
    updatedBooking.setId(lastInsertedBooking.getId());



    //act
    bookingRepository.updateBooking(updatedBooking);

    //assert
    assertEquals(lastInsertedBooking.getId(), updatedBooking.getId(), "Id must be the same");
    assertNotEquals(lastInsertedBooking.getUserId(), updatedBooking.getUserId(), "UserId must not match");
    assertNotEquals(lastInsertedBooking.getVehicleNumber(), updatedBooking.getVehicleNumber(), "VehicleNumber must not match");
    assertNotEquals(lastInsertedBooking.getPickupPointId(), updatedBooking.getPickupPointId(), "PickupPoint must not match");
    assertNotEquals(lastInsertedBooking.getSubscriptionName(), updatedBooking.getSubscriptionName(), "SubscriptionName must not match");

  }

  @Test
  @Order(4)
  void testDelete() {
    //act
    bookingRepository.delete(lastInsertedBooking);
    Booking booking = bookingRepository.findByBookingId(lastInsertedPickupPoint.getId());

    //assert
    assertEquals(null, booking, "Must be null");
  }

}