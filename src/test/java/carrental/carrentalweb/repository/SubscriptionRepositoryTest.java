package carrental.carrentalweb.repository;

import carrental.carrentalweb.entities.Subscription;
import carrental.carrentalweb.entity_factories.TestSubscriptionFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


// Mads
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
     * Cacher den injectede database service
     *
     * Indeholder også Arrange delen for flere af
     * testenes AAA pattern.
     */
    @BeforeAll
    public static void init(DatabaseService databaseService) {
        database = databaseService;

        // Arrange
        repository = new SubscriptionRepository(database);
    }

    /*
    * Test af SubscriptionRepository.create()
    * tester om metoden opretter en række i database tabellen.
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

    /*
     * Test af SubscriptionRepository.get()
     * tester om metoden returnerer et Subscription objekt.
     */
    @Test
    @Order(2)
    public void testGet_ReturnsSubscriptionObjectFromDatabase() {
        // Act
        repository.create(testSubscription);
        lastInsertedSubscription = repository.last();
        Subscription subscription = repository.get("name", lastInsertedSubscription.getName());

        // Assert
        assertNotNull(subscription, "Subscription object not returned.");
    }

    /*
     * Test af SubscriptionRepository.update()
     * tester om metoden opdaterer en række i database tabellen.
     */
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

    /*
     * Test af SubscriptionRepository.delete()
     * tester om metoden fjerner en række i database tabellen.
     */
    @Test
    @Order(4)
    public void testDelete_SavesToDatabase() {
        // Act
        repository.delete(lastInsertedSubscription);
        Subscription subscription = repository.get("name", lastInsertedSubscription.getName());

        // Assert
        assertNull(subscription, "Subscription must be null");
    }

    /*
     * Test af SubscriptionRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(5)
    public void testParseResponseFirst_ReturnNullIfNoSubscriptionWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();

        // Act
        Subscription subscription = repository.parseResponseFirst(response);

        // Assert
        assertNull(subscription, "Subscription must be null");
    }

    /*
     * Test af SubscriptionRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
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

    /*
     * Test af SubscriptionRepository.parseReponse()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
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
        for (Subscription subscription : subscriptions) {
            assertEquals(record.map().get("name"), subscription.getName(), "Names must be equal");
            assertEquals((double) record.map().get("price"), subscription.getPrice(), "Prices must be equal");
            assertEquals((double) record.map().get("days"), subscription.getDays(), "Days must be equal");
        }
    }
}
