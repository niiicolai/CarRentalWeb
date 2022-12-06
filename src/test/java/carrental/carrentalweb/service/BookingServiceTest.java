package carrental.carrentalweb.service;

import carrental.carrentalweb.entities.Booking;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestBookingFactory;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.repository.BookingRepository;
import carrental.carrentalweb.repository.UserRepository;
import carrental.carrentalweb.services.BookingService;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)




public class BookingServiceTest {

    /*
     * The database version of the test user.
     */
    private static Booking testBooking;

    /*
     * The service being tested.
     */
    private static BookingService service;

    /*
     * The user repository used to create test data.
     */
    private static BookingRepository repository;

    /*
     * The database service connected to
     * a seperate test database version of the
     * original database.
     */
    private static DatabaseService database;

    @BeforeAll
    public static void init(DatabaseService databaseService) {
        database = databaseService;
        repository = new BookingRepository(database);
        testBooking = TestBookingFactory.create(7357, "PREMIUM", 1, 1);
        testBooking = repository.last();
        repository.createBooking(testBooking);

        // Arrange
        service = new BookingService(repository);
    }


    @Test
    @Order(1)
    public void testGetBookingAmountsOfTheWeek() {
    //Arrange
    BookingService bookingService = new BookingService(repository);

    //Act
    List<Integer> bookingAmount = bookingService.getBookingAmountsOfTheWeek();
        System.out.println(bookingAmount);

    //Assert
        Assert.assertNotNull(bookingAmount);
    }



}
