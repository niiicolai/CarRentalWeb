package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.entity_factories.TestCreditRatingFactory;
import carrental.carrentalweb.entity_factories.TestSubscriptionFactory;
import carrental.carrentalweb.enums.CreditRatingState;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Mads
@SpringBootTest
@ExtendWith(DatabaseParameterResolver.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubscriptionRepositoryTest {
    /*
     * testSubscription bliver oprettet af testSubscriptionFactory.
     * som bliver testet mod 'lastInsertedSubscription' som er den sidste
     * oprettet i databasen.
     */
    private static Subscription testSubscription;

    /*
     * Database versionen af
     * 'testSubscription' variablen.
     */
    private static Subscription lastInsertedSubscription;

    /*
     * Det repository der bliver udført test på.
     */
    private static SubscriptionRepository repository;

    /*
     * Database service forbundet til
     * en seperat test version af den
     * originale database.
     */
    private static DatabaseService database;

    /*
     * @BeforeAll bliver kørt før alle tests.
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
        repository = new SubscriptionRepository(database);
    }
    /*
     * Method: create(testSubscription); last();
     * Test if the method insert saves the credit rating to the database.
     * It uses the method last to check if the credit rating is saved, and
     * at the same time test if the last method works.
     */
    @Test
    @Order(1)
    public void testInsertAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testSubscription = TestSubscriptionFactory.create(true);

        // Act
        repository.create(testSubscription);
        lastInsertedSubscription = repository.last();

        // Assert
        assertEquals(testSubscription.getName(), lastInsertedSubscription.getName(), "Names must be equal");
        assertEquals(testSubscription.getDays(), lastInsertedSubscription.getDays(), "Days must be equal");
        assertEquals(testSubscription.getPrice(), lastInsertedSubscription.getPrice(), "Prices must be equal");
    }

    @Test
    @Order(2)
    public void testFind_ReturnsSubscriptionObjectFromDatabase() {
        // Act
        repository.create(testSubscription);
        lastInsertedSubscription = repository.last();
        Subscription subscription = repository.get("name", lastInsertedSubscription.getName());

        // Assert
        assertEquals(lastInsertedSubscription.getName(), subscription.getName(), "Name must be equal");
        assertEquals(lastInsertedSubscription.getPrice(), subscription.getPrice(), "Price must be equal");
        assertEquals(lastInsertedSubscription.getDays(), subscription.getDays(), "Days must be equal");
    }

    @Test
    @Order(3)
    public void testUpdate_SavesToDatabase() {
        // Arrange
        repository.create(testSubscription);
        lastInsertedSubscription = repository.last();

        // Act
        repository.update(lastInsertedSubscription);
        Subscription updatedSubscription = repository.get("name", lastInsertedSubscription.getName());

        // Assert
        assertEquals(lastInsertedSubscription.getName(), updatedSubscription.getName(), "Name must be equal");
        assertEquals(lastInsertedSubscription.getDays(), updatedSubscription.getDays(), "Days must be equal");
        assertEquals(lastInsertedSubscription.getPrice(), updatedSubscription.getPrice(), "Price must be equal");
    }


    @Test
    @Order(4)
    public void testDelete_SavesToDatabase() {
        // Act
        repository.delete(lastInsertedSubscription);
        Subscription subscription = repository.get("name", lastInsertedSubscription.getName());

        // Assert
        assertNull(subscription, "Subscription must be null");
    }

    @Test
    @Order(5)
    public void testParseResponseFirst_ReturnNullIfNoSubscriptionWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();

        // Act
        Subscription subscription = repository.parseResponseFirst(response);

        // Assert
        assertNull(subscription, "Subscription rating must be null");
    }

    @Test
    @Order(6)
    public void testParseResponseFirst_ReturnSubscriptionIfSubscriptionWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestSubscriptionFactory.createDatabaseRecord(true);
        response.add(record);

        // Act
        Subscription subscription = repository.parseResponseFirst(response);

        // Assert
        assertEquals(record.map().get("name"), subscription.getName(), "Names must be equal");
        assertEquals((double) record.map().get("price"), subscription.getPrice(), "Prices must be equal");
        assertEquals((double) record.map().get("days"), subscription.getDays(), "Days must be equal");
    }

    @Test
    @Order(7)
    public void testParseResponse_ReturnSubscriptionsIfSubscriptionsWereFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestSubscriptionFactory.createDatabaseRecord(true);
        response.add(record);

        // Act
        List<Subscription> subscriptions = repository.parseResponse(response);

        // Assert
        for (int i = 0; i < subscriptions.size(); i++) {
            Subscription subscription = subscriptions.get(i);
            assertEquals(record.map().get("name"), subscription.getName(), "Names must be equal");
            assertEquals((double) record.map().get("price"), subscription.getPrice(), "Prices must be equal");
            assertEquals((double) record.map().get("days"), subscription.getDays(), "Days must be equal");
        }
    }
}
