package carrental.carrentalweb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import carrental.carrentalweb.entities.CreditRating;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestCreditRatingFactory;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.enums.CreditRatingState;
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
public class CreditRatingRepositoryTest {
    
    /*
     * A user used created by the test user factory.
     * Its id is therefore equal to 0.
     * 'lastInsertedCreditRating' is the database version 
     * of the 'testCreditRating' variable.
     * 
     * It is different from the database version
     * because it is used to test against the
     * values of the database version.
     */
    private static CreditRating testCreditRating;

    /*
     * The database version of the 
     * 'testCreditRating' variable.
     */
    private static CreditRating lastInsertedCreditRating;

    /*
     * The database version of a 'testUser'.
     */
    private static User lastInsertedUser;

    /*
     * The repository that is being tested.
     */
    private static CreditRatingRepository creditRatingRepository;

    /*
     * The user repository used to create a test user
     * for the credit rating.
     */
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
        creditRatingRepository = new CreditRatingRepository(database);
    
        User user = TestUserFactory.create();
        userRepository = new UserRepository(databaseService);
        userRepository.insert(user);
        lastInsertedUser = userRepository.last();
    }

    /*
     * Executed after all tests.
     * Deletes the created database objects.
     */
    @AfterAll
    public static void after() {
        userRepository.delete(lastInsertedUser);
    }

    /*
     * Method: insert(creditRating); last();
     * Test if the method insert saves the credit rating to the database.
     * It uses the method last to check if the credit rating is saved, and
     * at the same time test if the last method works.
     */
    @Test
    @Order(1)  
    public void testInsertAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testCreditRating = TestCreditRatingFactory.create(CreditRatingState.Approve, lastInsertedUser.getId());

        // Act
        creditRatingRepository.insert(testCreditRating);
        lastInsertedCreditRating = creditRatingRepository.last();

        // Assert
        assertEquals(testCreditRating.getState(), lastInsertedCreditRating.getState(), "States must be equal");
        assertEquals(lastInsertedUser.getId(), lastInsertedCreditRating.getUserId(), "User id must be equal");
    }

    /*
     * Method: find(column, value);
     * Test if the method returns a credit rating object from the database.
     */
    @Test
    @Order(2)  
    public void testFind_ReturnsUserObjectFromDatabase() {
        // Act
        CreditRating creditRating = creditRatingRepository.find("user_id", lastInsertedCreditRating.getUserId());

        // Assert
        assertEquals(lastInsertedCreditRating.getState(), creditRating.getState(), "States must be equal");
        assertEquals(lastInsertedUser.getId(), creditRating.getUserId(), "User ids must be equal");
    }

    /*
     * Method: update(creditRating);
     * Test if the method actual updates the credit ratings's details in the database.
     */
    @Test
    @Order(3)  
    public void testUpdate_SavesToDatabase() {
        // Arrange        
        CreditRatingState nextState = lastInsertedCreditRating.getState() == CreditRatingState.Approve ?
            CreditRatingState.Reject : CreditRatingState.Approve;
        lastInsertedCreditRating.setState(nextState);

        // Act
        creditRatingRepository.update(lastInsertedCreditRating);
        CreditRating updatedCreditRating = creditRatingRepository.find("user_id", lastInsertedCreditRating.getUserId());

        // Assert
        assertEquals(lastInsertedUser.getId(), updatedCreditRating.getUserId(), "User ids must be equal");
        assertEquals(nextState, updatedCreditRating.getState(), "States must not be equal");
    }

    /*
     * Method: delete(user);
     * Test if the method actual deletes a credit rating from the database.
     */
    @Test
    @Order(4)  
    public void testDelete_SavesToDatabase() {
        // Act
        creditRatingRepository.delete(lastInsertedCreditRating);
        CreditRating creditRating = creditRatingRepository.find("user_id", lastInsertedCreditRating.getUserId());

        // Assert
        assertEquals(null, creditRating, "creditRating must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns null if no credit rating was found
     * in the database response.
     */
    @Test
    @Order(5)
    public void testParseResponseFirst_ReturnNullIfNoCreditRatingWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        
        // Act
        CreditRating creditRating = creditRatingRepository.parseResponseFirst(response);

        // Assert
        assertEquals(null, creditRating, "Credit rating must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns a credit rating object containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(6)
    public void testParseResponseFirst_ReturnCreditRatingIfCreditRatingWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestCreditRatingFactory.createDatabaseRecord(CreditRatingState.Approve, lastInsertedUser.getId());
        response.add(record);

        // Act
        CreditRating creditRating = creditRatingRepository.parseResponseFirst(response);

        // Assert
        assertEquals((long) record.map().get("user_id"), creditRating.getUserId(), "Ids must be equal");
        assertEquals((String) record.map().get("state"), creditRating.getState().toString(), "States must be equal");
    }

    /*
     * Method: parseResponse(databaseResponse);
     * Test if the method returns a list of credit rating objects containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(7)
    public void testParseResponse_ReturnCreditRatingsIfCreditRatingsWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestCreditRatingFactory.createDatabaseRecord(CreditRatingState.Approve, lastInsertedUser.getId());
        response.add(record);

        // Act
        List<CreditRating> creditRatings = creditRatingRepository.parseResponse(response);

        // Assert
        for (int i = 0; i < creditRatings.size(); i++) {
            CreditRating creditRating = creditRatings.get(i);
            assertEquals((long) record.map().get("user_id"), creditRating.getUserId(), "Ids must be equal");
            assertEquals((String) record.map().get("state"), creditRating.getState().toString(), "States must be equal");
        }
    }
}
