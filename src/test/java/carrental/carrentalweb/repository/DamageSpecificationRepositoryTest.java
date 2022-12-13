package carrental.carrentalweb.repository;


import carrental.carrentalweb.entities.DamageSpecification;
import carrental.carrentalweb.entity_factories.TestDamageSpecificationFactory;
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
public class DamageSpecificationRepositoryTest {

    /*
     * testDamageSpecification bliver oprettet af testDamageSpecificationFactory.
     * som bliver testet mod 'lastInsertedDamageSpecification' som er den sidste
     * oprettet i databasen.
     */
    private static DamageSpecification testDamageSpecification;

    /*
     * Database versionen af
     * 'testDamageSpecification' variablen.
     */
    private static DamageSpecification lastInsertedDamageSpecification;

    /*
     * Det repository der bliver udført test på.
     */
    private static DamageSpecificationRepository repository;

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
        repository = new DamageSpecificationRepository(database);
        testDamageSpecification = TestDamageSpecificationFactory.create();
    }

    /*
     * Test af DamageSpecificationRepository.create() og last()
     * tester om metoden opretter en række i database tabellen.
     */
    @Test
    @Order(1)
    public void testCreateAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        
        // Act
        repository.create(testDamageSpecification);
        lastInsertedDamageSpecification = repository.last();

        // Assert
        assertEquals(testDamageSpecification.getDescription(), lastInsertedDamageSpecification.getDescription(), "Description must be equal");
        assertEquals(testDamageSpecification.getPrice(), lastInsertedDamageSpecification.getPrice(), "Prices must be equal");
    
    }

    /*
     * Test af DamageSpecificationRepository.get()
     * tester om metoden returnerer et DamageSpecification objekt.
     */
    @Test
    @Order(2)
    public void testGet_ReturnsDamageSpecificationObjectFromDatabase() {
        // Act
        DamageSpecification damageSpecification = repository.get("description", lastInsertedDamageSpecification.getDescription());

        // Assert
        assertEquals(lastInsertedDamageSpecification.getDescription(), damageSpecification.getDescription(), "Description must be equal");
        assertEquals(lastInsertedDamageSpecification.getPrice(), damageSpecification.getPrice(), "Prices must be equal");
    }

    /*
     * Test af DamageSpecificationRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(3)
    public void testParseResponseFirst_ReturnNullIfNoDamageSpecificationWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();

        // Act
        DamageSpecification damageSpecification = repository.parseResponseFirst(response);

        // Assert
        assertNull(damageSpecification, "DamageSpecification must be null");
    }

    /*
     * Test af DamageSpecificationRepository.parseReponseFirst()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(4)
    public void testParseResponseFirst_ReturnDamageSpecificationIfDamageSpecificationWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestDamageSpecificationFactory.createDatabaseRecord(true);
        response.add(record);

        // Act
        DamageSpecification damageSpecification = repository.parseResponseFirst(response);

        // Assert
        assertEquals(record.map().get("description"), damageSpecification.getDescription(), "Description must be equal");
        assertEquals((double) record.map().get("price"), damageSpecification.getPrice(), "Prices must be equal");
    }

    /*
     * Test af DamageSpecificationRepository.parseReponse()
     * tester om metoden parser DatabaseResponse rigtigt.
     */
    @Test
    @Order(5)
    public void testParseResponse_ReturnDamageSpecificationsIfDamageSpecificationsWereFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestDamageSpecificationFactory.createDatabaseRecord(true);
        response.add(record);

        // Act
        List<DamageSpecification> damageSpecifications = repository.parseResponse(response);

        // Assert
        for (DamageSpecification damageSpecification : damageSpecifications) {
            assertEquals(record.map().get("description"), damageSpecification.getDescription(), "Description must be equal");
            assertEquals((double) record.map().get("price"), damageSpecification.getPrice(), "Prices must be equal");
        }
    }
}
