package carrental.carrentalweb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.Car;
import carrental.carrentalweb.entities.Invoice;
import carrental.carrentalweb.entities.InvoiceSpecification;
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestBookingFactory;
import carrental.carrentalweb.entity_factories.TestCarFactory;
import carrental.carrentalweb.entity_factories.TestInvoiceFactory;
import carrental.carrentalweb.entity_factories.TestInvoiceSpecificationFactory;
import carrental.carrentalweb.entity_factories.TestPickupPointFactory;
import carrental.carrentalweb.entity_factories.TestSubscriptionFactory;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * @ExtendWith(DatabaseParameterResolver.class)
 * Injects the Database Service object.
 * 
 * @TestMethodOrder(OrderAnnotation.class)
 * Enable's the @Order(int) annotation allowing
 * to set the order the tests should be executed in.
 */
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(OrderAnnotation.class)
public class InvoiceSpecificationRepositoryTest {
    
    /*
     * A user used created by the test user factory.
     * Its id is therefore equal to 0.
     * 'lastInsertedInvoiceSpecification' is the database version 
     * of the 'testInvoiceSpecification' variable.
     * 
     * It is different from the database version
     * because it is used to test against the
     * values of the database version.
     */
    private static InvoiceSpecification testInvoiceSpecification;

    /*
     * The database version of the 
     * 'testInvoiceSpecification' variable.
     */
    private static InvoiceSpecification lastInsertedInvoiceSpecification;

    /*
     * Extra Objects used to test the invoice
     */
    private static Booking lastInsertedBooking;
    private static Subscription lastInsertedSubscription;
    private static Car lastInsertedCar;
    private static PickupPoint lastInsertedPickupPoint;
    private static User lastInsertedUser;
    private static Invoice lastInsertedInvoice;

    /*
     * The repository that is being tested.
     */
    private static InvoiceSpecificationRepository invoiceSpecificationRepository;

    /*
     * Extra repositories used to create extra test objects
     * for the invoice.
     */
    private static BookingRepository bookingRepository;
    private static SubscriptionRepository subscriptionRepository;
    private static CarRepository carRepository;
    private static PickupPointRepository pickupPointRepository;
    private static UserRepository userRepository;
    private static InvoiceRepository invoiceRepository;

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
        // Create test repositories
        invoiceSpecificationRepository = new InvoiceSpecificationRepository(database);
        subscriptionRepository = new SubscriptionRepository(database);
        carRepository = new CarRepository(database);
        pickupPointRepository = new PickupPointRepository(database);
        userRepository = new UserRepository(database);
        bookingRepository = new BookingRepository(database);
        invoiceRepository = new InvoiceRepository(database);

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

        // Create test booking
        Booking booking = TestBookingFactory.create(lastInsertedPickupPoint.getId(), lastInsertedSubscription.getName(),
            lastInsertedUser.getId(), lastInsertedCar.getVehicleNumber());
        bookingRepository.createBooking(booking);
        lastInsertedBooking = bookingRepository.last();

        // Create test invoice
        invoiceRepository.insert(TestInvoiceFactory.create(lastInsertedBooking.getId()));
        lastInsertedInvoice = invoiceRepository.last();
    }

    /*
     * Executed after all tests.
     * Deletes the created database objects.
     */
    @AfterAll
    public static void after() {
        invoiceRepository.delete(lastInsertedInvoice);
        bookingRepository.delete(lastInsertedBooking);
        subscriptionRepository.delete(lastInsertedSubscription);
        pickupPointRepository.delete(lastInsertedPickupPoint);
        userRepository.delete(lastInsertedUser);
        carRepository.deleteCarByVehicleNumber(lastInsertedCar.getVehicleNumber());
    }

    /*
     * Method: insert(invoiceSpecification); last();
     * Test if the method insert saves the invoice specification to the database.
     * It uses the method last to check if the invoice specification is saved, and
     * at the same time test if the last method works.
     */
    @Test
    @Order(1)  
    public void testInsertAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testInvoiceSpecification = TestInvoiceSpecificationFactory.create(lastInsertedInvoice.getId());

        // Act
        invoiceSpecificationRepository.insert(testInvoiceSpecification);
        lastInsertedInvoiceSpecification = invoiceSpecificationRepository.last();

        // Assert
        assertEquals(testInvoiceSpecification.getDescription(), lastInsertedInvoiceSpecification.getDescription(), "Descriptions must be equal");
        assertEquals(testInvoiceSpecification.getPrice(), lastInsertedInvoiceSpecification.getPrice(), "Prices must be equal");
        assertEquals(lastInsertedInvoice.getId(), lastInsertedInvoiceSpecification.getInvoiceId(), "Invoice ids must be equal");
    }

    /*
     * Method: find(column, value);
     * Test if the method returns a invoice specification object from the database.
     */
    @Test
    @Order(2)  
    public void testFind_ReturnInvoiceObjectFromDatabase() {
        // Act
        InvoiceSpecification invoiceSpecification = invoiceSpecificationRepository.find("id", lastInsertedInvoiceSpecification.getId());

        // Assert
        assertEquals(lastInsertedInvoiceSpecification.getDescription(), invoiceSpecification.getDescription(), "Descriptions must be equal");
        assertEquals(lastInsertedInvoiceSpecification.getPrice(), invoiceSpecification.getPrice(), "Prices must be equal");
        assertEquals(lastInsertedInvoice.getId(), invoiceSpecification.getInvoiceId(), "Invoice ids must be equal");
        assertEquals(lastInsertedInvoiceSpecification.getId(), invoiceSpecification.getId(), "Ids must be equal");
    }

    /*
     * Method: delete(invoiceSpecification);
     * Test if the method actual deletes a invoice specification from the database.
     */
    @Test
    @Order(3)  
    public void testDelete_SavesToDatabase() {
        // Act
        invoiceSpecificationRepository.delete(lastInsertedInvoiceSpecification);
        InvoiceSpecification invoiceSpecification = invoiceSpecificationRepository.find("id", lastInsertedInvoiceSpecification.getId());

        // Assert
        assertEquals(null, invoiceSpecification, "invoice specification must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns null if no invoice specification was found
     * in the database response.
     */
    @Test
    @Order(4)
    public void testParseResponseFirst_ReturnNullIfNoInvoiceSpecificationWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        
        // Act
        InvoiceSpecification invoiceSpecification = invoiceSpecificationRepository.parseResponseFirst(response);

        // Assert
        assertEquals(null, invoiceSpecification, "Invoice specification must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns a invoice specification object containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(5)
    public void testParseResponseFirst_ReturnInvoiceSpecificationIfInvoiceSpecificationWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestInvoiceSpecificationFactory.createDatabaseRecord(lastInsertedBooking.getId());
        response.add(record);

        // Act
        InvoiceSpecification invoiceSpecification = invoiceSpecificationRepository.parseResponseFirst(response);
        
        // Assert
        assertEquals((String) record.map().get("description"), invoiceSpecification.getDescription(), "Descriptions must be equal");
        assertEquals((Double) record.map().get("price"), invoiceSpecification.getPrice(), "Prices must be equal");
        assertEquals((long) record.map().get("invoice_id"), invoiceSpecification.getInvoiceId(), "Invoice ids must be equal");
        assertEquals((long) record.map().get("id"), invoiceSpecification.getId(), "Ids must be equal");
    }

    /*
     * Method: parseResponse(databaseResponse);
     * Test if the method returns a list of invoice specification objects containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(6)
    public void testParseResponse_ReturnInvoiceSpecificationsIfInvoiceSpecificationsWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestInvoiceSpecificationFactory.createDatabaseRecord(lastInsertedBooking.getId());
        response.add(record);

        // Act
        List<InvoiceSpecification> invoiceSpecifications = invoiceSpecificationRepository.parseResponse(response);

        // Assert
        for (int i = 0; i < invoiceSpecifications.size(); i++) {
            InvoiceSpecification invoiceSpecification = invoiceSpecifications.get(i);
            assertEquals((String) record.map().get("description"), invoiceSpecification.getDescription(), "Descriptions must be equal");
            assertEquals((Double) record.map().get("price"), invoiceSpecification.getPrice(), "Prices must be equal");
            assertEquals((long) record.map().get("invoice_id"), invoiceSpecification.getInvoiceId(), "Invoice ids must be equal");
            assertEquals((long) record.map().get("id"), invoiceSpecification.getId(), "Ids must be equal");
        }
    }
}
