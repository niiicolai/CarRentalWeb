package carrental.carrentalweb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.entity_factories.TestUserFactory;
import carrental.carrentalweb.parameter_resolvers.DatabaseParameterResolver;
import carrental.carrentalweb.repository.UserRepository;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.services.UserService;

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
public class UserServiceTest {
    
    /*
     * The database version of the test user.
     */
    private static User testUser;

    /*
     * The service being tested.
     */
    private static UserService service;

    /*
     * The user repository used to create test data.
     */
    private static UserRepository repository;

    /*
     * The database service connected to
     * a seperate test database version of the 
     * original database.
     */
    private static DatabaseService database;

    /*
     * Executed before all tests.
     * Caches the injected database service,
     * creates an instance of the user repository, 
     * and creates an instance of the user service.
     * 
     * It also contains the arrange part
     * of several tests' AAA pattern.
     */
    @BeforeAll
    public static void init(DatabaseService databaseService) {
        database = databaseService;
        repository = new UserRepository(database);
        testUser = TestUserFactory.create();
        repository.insert(testUser);
        testUser = repository.last();

        // Arrange
        service = new UserService(repository);
    }

    /*
     * Executed after all tests.
     * Deletes the created database user.
     */
    @AfterAll
    public static void after() {
        repository.delete(testUser);
    }

    /*
     * Method: loadUserByUsername(username);
     * Test if the method returns the exception UsernameNotFoundException,
     * if no user was found.
     */
    @Test
    @Order(1)
    public void testLoadUserByUsername_ReturnsUsernameNotFoundException() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, 
            () -> { service.loadUserByUsername(""); }, "Method should throw UsernameNotFoundException");
    }

    /*
     * Method: loadUserByUsername(username);
     * Test if the method returns a user from the database,
     * if a user was found.
     */
    @Test
    @Order(2)
    public void testLoadUserByUsername_ReturnsUserFromDatabase() {
        // Act
        UserDetails user = (UserDetails) service.loadUserByUsername(testUser.getUsername());
        // Assert
        assertEquals(user.getUsername(), testUser.getUsername(), "Usernames must be equal");
    }
}
