package carrental.carrentalweb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
import carrental.carrentalweb.entities.PickupPoint;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestBookingFactory;
import carrental.carrentalweb.entity_factories.TestCarFactory;
import carrental.carrentalweb.entity_factories.TestInvoiceFactory;
import carrental.carrentalweb.entity_factories.TestPickupPointFactory;
import carrental.carrentalweb.entity_factories.TestSubscriptionFactory;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;


/*
 * @SpringBootTest
 * Define the class as Test class.
 * 
 * @ExtendWith(DatabaseParameterResolver.class)
 * Injects the Database Service object.
 * 
 * @TestMethodOrder(OrderAnnotation.class)
 * Enable's the @Order(int) annotation allowing
 * to set the order the tests should be executed in.
 */
@SpringBootTest
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(OrderAnnotation.class)
public class InvoiceRepositoryTest {
    
    /*
     * A user used created by the test user factory.
     * Its id is therefore equal to 0.
     * 'lastInsertedInvoice' is the database version 
     * of the 'testInvoice' variable.
     * 
     * It is different from the database version
     * because it is used to test against the
     * values of the database version.
     */
    private static Invoice testInvoice;

    /*
     * The database version of the 
     * 'testInvoice' variable.
     */
    private static Invoice lastInsertedInvoice;

    /*
     * Extra Objects used to test the invoice
     */
    private static Booking lastInsertedBooking;
    private static Subscription lastInsertedSubscription;
    private static Car lastInsertedCar;
    private static PickupPoint lastInsertedPickupPoint;
    private static User lastInsertedUser;

    /*
     * The repository that is being tested.
     */
    private static InvoiceRepository invoiceRepository;

    /*
     * Extra repositories used to create extra test objects
     * for the invoice.
     */
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
        // Create test repositories
        subscriptionRepository = new SubscriptionRepository(database);
        carRepository = new CarRepository(database);
        pickupPointRepository = new PickupPointRepository(database);
        userRepository = new UserRepository(database);
        invoiceRepository = new InvoiceRepository(database);
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

        // Create test booking
        Booking booking = TestBookingFactory.create(lastInsertedPickupPoint.getId(), lastInsertedSubscription.getName(),
            lastInsertedUser.getId(), lastInsertedCar.getVehicleNumber());
        bookingRepository.createBooking(booking);
        lastInsertedBooking = bookingRepository.last();
    }

    /*
     * Executed after all tests.
     * Deletes the created database objects.
     */
    @AfterAll
    public static void after() {
        bookingRepository.delete(lastInsertedBooking);
        subscriptionRepository.delete(lastInsertedSubscription);
        pickupPointRepository.delete(lastInsertedPickupPoint);
        userRepository.delete(lastInsertedUser);
        carRepository.deleteCarByVehicleNumber(lastInsertedCar.getVehicleNumber());
    }

    /*
     * Method: insert(invoice); last();
     * Test if the method insert saves the invoice to the database.
     * It uses the method last to check if the invoice is saved, and
     * at the same time test if the last method works.
     */
    @Test
    @Order(1)  
    public void testInsertAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testInvoice = TestInvoiceFactory.create(lastInsertedBooking.getId());

        // Act
        invoiceRepository.insert(testInvoice);
        lastInsertedInvoice = invoiceRepository.last();

        // Assert
        assertNotEquals(0L, lastInsertedInvoice.getId(), "Id must not be 0");
        assertEquals(testInvoice.getDueDate().getMinute(), lastInsertedInvoice.getDueDate().getMinute(), "Dues must be equal");
        assertEquals(testInvoice.getPaidAt().getMinute(), lastInsertedInvoice.getPaidAt().getMinute(), "Paid ats must be equal");
        assertEquals(lastInsertedBooking.getId(), lastInsertedInvoice.getBookingId(), "Booking id must be equal");
    }

    /*
     * Method: find(column, value);
     * Test if the method returns a invoice object from the database.
     */
    @Test
    @Order(2)  
    public void testFind_ReturnInvoiceObjectFromDatabase() {
        // Act
        Invoice invoice = invoiceRepository.find("booking_id", lastInsertedInvoice.getBookingId());

        // Assert
        assertEquals(lastInsertedInvoice.getDueDate().getMinute(), invoice.getDueDate().getMinute(), "Dues must be equal");
        assertEquals(lastInsertedInvoice.getPaidAt().getMinute(), invoice.getPaidAt().getMinute(), "Paid ats must be equal");
        assertEquals(lastInsertedBooking.getId(), invoice.getBookingId(), "Booking id must be equal");
        assertEquals(lastInsertedInvoice.getId(), invoice.getId(), "Ids must be equal");
    }

    /*
     * Method: delete(user);
     * Test if the method actual deletes a invoice from the database.
     */
    @Test
    @Order(3)  
    public void testDelete_SavesToDatabase() {
        // Act
        invoiceRepository.delete(lastInsertedInvoice);
        Invoice invoice = invoiceRepository.find("booking_id", lastInsertedInvoice.getBookingId());

        // Assert
        assertEquals(null, invoice, "invoice must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns null if no invoice was found
     * in the database response.
     */
    @Test
    @Order(4)
    public void testParseResponseFirst_ReturnNullIfNoInvoiceWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        
        // Act
        Invoice invoice = invoiceRepository.parseResponseFirst(response);

        // Assert
        assertEquals(null, invoice, "Invoice must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns a invoice object containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(5)
    public void testParseResponseFirst_ReturnInvoiceIfInvoiceWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestInvoiceFactory.createDatabaseRecord(lastInsertedBooking.getId());
        response.add(record);

        // Act
        Invoice invoice = invoiceRepository.parseResponseFirst(response);

        // Assert
        assertEquals((LocalDateTime) record.map().get("due_date"), invoice.getDueDate(), "Dues must be equal");
        assertEquals((LocalDateTime) record.map().get("paid_at"), invoice.getPaidAt(), "Paid ats must be equal");
        assertEquals((long) record.map().get("booking_id"), invoice.getBookingId(), "Booking id must be equal");
        assertEquals((long) record.map().get("id"), invoice.getId(), "Ids must be equal");
    }

    /*
     * Method: parseResponse(databaseResponse);
     * Test if the method returns a list of invoice objects containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(6)
    public void testParseResponse_ReturnInvoicesIfInvoicesWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestInvoiceFactory.createDatabaseRecord(lastInsertedBooking.getId());
        response.add(record);

        // Act
        List<Invoice> invoices = invoiceRepository.parseResponse(response);

        // Assert
        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = invoices.get(i);
            assertEquals((LocalDateTime) record.map().get("due_date"), invoice.getDueDate(), "Dues must be equal");
            assertEquals((LocalDateTime) record.map().get("paid_at"), invoice.getPaidAt(), "Paid ats must be equal");
            assertEquals((long) record.map().get("booking_id"), invoice.getBookingId(), "Booking id must be equal");
            assertEquals((long) record.map().get("id"), invoice.getId(), "Ids must be equal");
        }
    }
}
