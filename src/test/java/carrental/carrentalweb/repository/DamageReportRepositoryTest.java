package carrental.carrentalweb.repository;


import carrental.carrentalweb.entities.*;
import carrental.carrentalweb.entity_factories.*;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Mads
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DamageReportRepositoryTest {
    /*
     * testDamageReport bliver oprettet af testDamageReportFactory.
     * som bliver testet mod 'lastInsertedDamageReport' som er den sidste
     * oprettet i databasen.
     */
    private static DamageReport testDamageReport;

    /*
     * Database versionen af
     * 'testDamageReport' variablen.
     */
    private static DamageReport lastInsertedDamageReport;

    /*
     * Det repository der bliver udført test på.
     */
    private static DamageReportRepository repository;
    private static BookingRepository bookingRepository;
    private static SubscriptionRepository subscriptionRepository;
    private static CarRepository carRepository;
    private static PickupPointRepository pickupPointRepository;
    private static UserRepository userRepository;

    private static Booking lastInsertedBooking;
    private static Subscription lastInsertedSubscription;
    private static Car lastInsertedCar;
    private static PickupPoint lastInsertedPickupPoint;
    private static User lastInsertedUser;


    /*
     * Database service forbundet til
     * en seperat test version af den
     * originale database.
     */
    private static DatabaseService database;

    /*
     * @BeforeAll bliver kørt før alle tests.
     * Cacher den injectede database service
     *
     * Indeholder også Arrange delen for flere af
     * testenes AAA pattern.
     */
    @BeforeAll
    public static void init(DatabaseService databaseService) {
        database = databaseService;

        // Arrange
        repository = new DamageReportRepository(database);
        bookingRepository = new BookingRepository(database);
        subscriptionRepository = new SubscriptionRepository(database);
        carRepository = new CarRepository(database);
        pickupPointRepository = new PickupPointRepository(database);
        userRepository = new UserRepository(database);

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

        bookingRepository.createBooking(TestBookingFactory.create(lastInsertedPickupPoint.getId(), lastInsertedSubscription.getName(),
                lastInsertedUser.getId(), lastInsertedCar.getVehicleNumber()));
        lastInsertedBooking = bookingRepository.last();
    }
    /*
     * Køres efter alle tests.
     * Sletter database objekter
     *
     * Jeg har kommenteret delete actions i after() her pga.
     * det ikke kommer til at fungere som det er sat op på nuværende tidspunkt,
     * fordi koden fejler på at slette en booking pga. en foreign key constraint
     * til damage report systemet. 
    @AfterAll
    public static void after() {
        bookingRepository.delete(lastInsertedBooking);
        subscriptionRepository.delete(lastInsertedSubscription);
        pickupPointRepository.delete(lastInsertedPickupPoint);
        userRepository.delete(lastInsertedUser);
        carRepository.deleteCarByVehicleNumber(lastInsertedCar.getVehicleNumber());
    }
    */

    /*
     * Test af DamageReportRepository.create() og last()
     * tester om metoden opretter en række i database tabellen.
     */
    @Test
    @Order(1)
    public void testCreateAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testDamageReport = TestDamageReportFactory.create(lastInsertedBooking.getId());

        List<String> descriptions = new ArrayList<String>();
        descriptions.add(TestDamageSpecificationFactory.getValidDescription());

        // Act
        repository.create(descriptions, lastInsertedBooking.getId());
        lastInsertedDamageReport = repository.last();

        // Assert
        assertEquals(testDamageReport.getBookingId(), lastInsertedDamageReport.getBookingId(), "Booking ID must be equal");
    }

    /*
     * Test af DamageReportRepository.get()
     * tester om metoden returnerer et DamageReport objekt.
     */
    @Test
    @Order(2)
    public void testGet_ReturnsDamageReportObjectFromDatabase() {

        // Act
        DamageReport damageReport = repository.get("booking_id", lastInsertedDamageReport.getBookingId());

        // Assert
        assertEquals(lastInsertedDamageReport.getBookingId(), damageReport.getBookingId(), "Booking ID must be equal");
    }

    /*
     * Test af DamageReportRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(3)
    public void testParseResponseFirst_ReturnNullIfNoDamageReportWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();

        // Act
        DamageReport damageReport = repository.parseResponseFirst(response);

        // Assert
        assertNull(damageReport, "DamageReport must be null");
    }

    /*
     * Test af DamageReportRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(4)
    public void testParseResponseFirst_ReturnDamageReportIfDamageReportWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestDamageReportFactory.createDatabaseRecord(lastInsertedDamageReport.getBookingId());
        response.add(record);

        // Act
        DamageReport damageReport = repository.parseResponseFirst(response);

        // Assert
        assertEquals(record.map().get("booking_id"), damageReport.getBookingId(), "Booking IDs must be equal");
    }

    /*
     * Test af DamageReportRepository.parseReponse()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(5)
    public void testParseResponse_ReturnDamageReportsIfDamageReportsWereFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestDamageReportFactory.createDatabaseRecord(lastInsertedDamageReport.getBookingId());
        response.add(record);

        // Act
        List<DamageReport> damageReports = repository.parseResponse(response);

        // Assert
        for (DamageReport damageReport : damageReports) {
            assertEquals(record.map().get("booking_id"), damageReport.getBookingId(), "Booking IDs must be equal");
        }
    }
}
