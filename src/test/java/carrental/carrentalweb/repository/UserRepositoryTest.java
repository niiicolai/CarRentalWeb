package carrental.carrentalweb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import carrental.carrentalweb.entities.User;
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
public class UserRepositoryTest {

    /*
     * A user used created by the test user factory.
     * Its id is therefore equal to 0.
     * 'lastInsertedUser' is the database version 
     * of the 'testUser' variable.
     * 
     * It is different from the database version
     * because it is used to test against the
     * values of the database version.
     */
    private static User testUser;

    /*
     * The database version of the 
     * 'testUser' variable.
     */
    private static User lastInsertedUser;

    /*
     * The repository that is being tested.
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
        repository = new UserRepository(database);
    }

    /*
     * Method: insert(user); last(user);
     * Test if the method insert saves the user to the database.
     * It uses the method last to check if the user is saved, and
     * at the same time test if the last method works.
     */
    @Test
    @Order(1)  
    public void testInsertAndLast_SaveToDatabase_AndReturnDatabaseObject() {
        // Arrange
        testUser = TestUserFactory.create();
        // Because the insert method encode the password,
        // it's required to cache the og. password here.
        String password = testUser.getPassword();

        // Act
        repository.insert(testUser);
        lastInsertedUser = repository.last();

        // Assert
        assertNotEquals(0, lastInsertedUser.getId(), "Last inserted user's id must not be 0");
        assertEquals(testUser.getUsername(), lastInsertedUser.getUsername(), "Usernames must be equal");
        assertEquals(testUser.getEmail(), lastInsertedUser.getEmail(), "emails must be equal");
        assertEquals(testUser.getAuthorities(), lastInsertedUser.getAuthorities(), "authorities must be equal");
        assertEquals(testUser.isEnabled(), lastInsertedUser.isEnabled(), "enabled must be equal");
        assertEquals(testUser.isAccountNonLocked(), lastInsertedUser.isAccountNonLocked(), "accountNonLocked must be equal");
        assertEquals(testUser.isAccountNonExpired(), lastInsertedUser.isAccountNonExpired(), "accountNonExpired must be equal");
        assertEquals(testUser.isCredentialsNonExpired(), lastInsertedUser.isCredentialsNonExpired(), "credentialsNonExpired must be equal");

        // If the last inserted user's password is not null,
        // and the password doesn't match the og. password,
        // it means the password is saved encrypted or as something else.
        assertNotNull(lastInsertedUser.getPassword(), "Password must not be null");
        assertNotEquals("", lastInsertedUser.getPassword(), "Password must not be empty");
        assertNotEquals(password, lastInsertedUser.getPassword(), "Passwords must not be equal");
    }

    /*
     * Method: insert(user);
     * Test if the method to ensure the password is not saved,
     * empty, null, or as the original version.
     */
    @Test
    @Order(2)  
    public void testInsertAndLast_SavesThePasswordEncoded() {
        // Arrange
        User testUser2 = TestUserFactory.create();
        // Because the insert method encode the password,
        // it's required to cache the og. password here.
        String password = testUser2.getPassword();

        // Act
        repository.insert(testUser2);
        User last = repository.last();

        // Assert
        assertNotEquals(0, last.getId(), "Last inserted user's id must not be 0");
        // If the last user's password is not null,
        // and the password doesn't match the og. password,
        // it means the password is saved encrypted or as something else.
        assertNotNull(last.getPassword(), "Password must not be null");
        assertNotEquals("", last.getPassword(), "Password must not be empty");
        assertNotEquals(password, last.getPassword(), "Passwords must not be equal");
        // but since we know the insert method encodes the password,
        // the testUser2 and last user's password should be equal.
        assertEquals(testUser2.getPassword(), last.getPassword(), "Passwords must be equal");

        // Delete test user 2
        repository.delete(last);
    }

    /*
     * Method: lastWithoutRole();
     * Test if the method returns the last created user without a role.
     */
    @Test
    @Order(3)  
    public void testLastWithoutRole_ReturnsUserObjectFromDatabase() {
        // Arrange
        User testUser2 = TestUserFactory.create();
        repository.insert(testUser2);

        // Act
        User user = repository.lastWithoutRole();
        LinkedList<GrantedAuthority> expectedRoles = new LinkedList<GrantedAuthority>();
        expectedRoles.add(new SimpleGrantedAuthority("ROLE_null"));

        // Assert
        assertNotEquals(0, user.getId(), "Id must not be 0");
        assertEquals(testUser2.getUsername(), user.getUsername(), "Usernames must be equal");
        assertEquals(testUser2.getEmail(), user.getEmail(), "emails must be equal");
        assertEquals(expectedRoles, user.getAuthorities(), "authorities must be equal");
        assertEquals(testUser2.isEnabled(), user.isEnabled(), "enabled must be equal");
        assertEquals(testUser2.isAccountNonLocked(), user.isAccountNonLocked(), "accountNonLocked must be equal");
        assertEquals(testUser2.isAccountNonExpired(), user.isAccountNonExpired(), "accountNonExpired must be equal");
        assertEquals(testUser2.isCredentialsNonExpired(), user.isCredentialsNonExpired(), "credentialsNonExpired must be equal");

        // Delete test user 2
        repository.delete(user);
    }

    /*
     * Method: find(column, value);
     * Test if the method returns a user object from the database.
     */
    @Test
    @Order(4)  
    public void testFind_ReturnsUserObjectFromDatabase() {
        // Act
        User user = repository.find("id", lastInsertedUser.getId());

        // Assert
        assertEquals(lastInsertedUser.getId(), user.getId(), "Ids must be equal");
        assertEquals(testUser.getUsername(), user.getUsername(), "Usernames must be equal");
        assertEquals(testUser.getEmail(), user.getEmail(), "emails must be equal");
        assertEquals(testUser.getAuthorities(), user.getAuthorities(), "authorities must be equal");
        assertEquals(testUser.isEnabled(), user.isEnabled(), "enabled must be equal");
        assertEquals(testUser.isAccountNonLocked(), user.isAccountNonLocked(), "accountNonLocked must be equal");
        assertEquals(testUser.isAccountNonExpired(), user.isAccountNonExpired(), "accountNonExpired must be equal");
        assertEquals(testUser.isCredentialsNonExpired(), user.isCredentialsNonExpired(), "credentialsNonExpired must be equal");
    }

    /*
     * Method: update(user);
     * Test if the method actual updates the user's details in the database.
     * And it also checks if the password changes, which is not allowed using the method update(user).
     * (Use updatePassword(user) instead.)
     */
    @Test
    @Order(5)  
    public void testUpdate_SavesToDatabase() {
        // Arrange
        User modifiedUser = TestUserFactory.create();
        modifiedUser.setPassword(LocalDateTime.now().toString());
        modifiedUser.setId(lastInsertedUser.getId());

        // Act
        repository.update(modifiedUser);
        User updatedUser = repository.find("id", lastInsertedUser.getId());

        // Assert
        assertEquals(lastInsertedUser.getId(), updatedUser.getId(), "Ids must be equal");
        assertEquals(modifiedUser.getUsername(), updatedUser.getUsername(), "Usernames must be equal");
        assertEquals(modifiedUser.getEmail(), updatedUser.getEmail(), "emails must be equal");
        assertEquals(modifiedUser.getAuthorities(), updatedUser.getAuthorities(), "authorities must be equal");
        assertEquals(modifiedUser.isEnabled(), updatedUser.isEnabled(), "enabled must be equal");
        assertEquals(modifiedUser.isAccountNonLocked(), updatedUser.isAccountNonLocked(), "accountNonLocked must be equal");
        assertEquals(modifiedUser.isAccountNonExpired(), updatedUser.isAccountNonExpired(), "accountNonExpired must be equal");
        assertEquals(modifiedUser.isCredentialsNonExpired(), updatedUser.isCredentialsNonExpired(), "credentialsNonExpired must be equal");
        // Ensure the updatedUser's password doesn't match the modifiedUser.
        assertNotEquals(modifiedUser.getPassword(), updatedUser.getPassword(), "Passwords must not be equal");
    }

    /*
     * Method: updatePassword(user);
     * Test if the method actual updates the user's password in the database.
     */
    @Test
    @Order(6)  
    public void testUpdatePassword_SavesToDatabase() {
        // Arrange
        lastInsertedUser.setPassword(LocalDateTime.now().toString());

        // Act
        repository.updatePassword(lastInsertedUser);
        User updatedUser = repository.find("id", lastInsertedUser.getId());

        // Assert
        assertEquals(lastInsertedUser.getId(), updatedUser.getId(), "Ids must be equal");
        // Note: The updatePassword method encode the lastInsertedUser object's password.
        // So the password of lastInsertedUser and updatedUser should be equal,
        // if the updatePassword method works.
        assertEquals(lastInsertedUser.getPassword(), updatedUser.getPassword(), "Password must not be equal");
    }

    /*
     * Method: updatePassword(user);
     * Test if the method to ensure the password is not saved as empty, null,
     * and that the password is something else than the original version.
     */
    @Test
    @Order(7)  
    public void testUpdatePassword_SavesThePasswordEncoded() {
        // Arrange
        String unencodedPassword = LocalDateTime.now().toString();
        lastInsertedUser.setPassword(unencodedPassword);

        // Act
        repository.updatePassword(lastInsertedUser);
        User updatedUser = repository.find("id", lastInsertedUser.getId());

        // Assert
        // Ensure the lastInsertedUser is the updatedUser
        assertEquals(lastInsertedUser.getId(), updatedUser.getId(), "Ids must be equal");
        // If the updated user's password is not null,
        // and the password doesn't match the last inserted user's original password,
        // it means the password is saved encrypted or as something else.
        assertNotNull(updatedUser.getPassword(), "Password must not be null");
        assertNotEquals("", updatedUser.getPassword(), "Password must not be empty");
        assertNotEquals(unencodedPassword, updatedUser.getPassword(), "Password must not be equal to unencodedPassword");
        // but since we know the updatePassword method encodes the password,
        // the lastInsertedUser and updatedUser's password should be equal.
        assertEquals(lastInsertedUser.getPassword(), updatedUser.getPassword(), "Passwords must be equal");
    }

    /*
     * Method: disable(user);
     * Test if the disable method actual disables a user in the database.
     */
    @Test
    @Order(8)  
    public void testDisable_SavesToDatabase() {
        // Act
        repository.disable(lastInsertedUser);
        User last = repository.find("id", lastInsertedUser.getId()); 

        // Assert
        assertEquals(lastInsertedUser.getId(), last.getId(), "Ids must be equal");
        assertEquals(false, last.isEnabled(), "enabled must be false");
    }

    /*
     * Method: delete(user);
     * Test if the method actual deletes a user from the database.
     */
    @Test
    @Order(9)  
    public void testDelete_SavesToDatabase() {
        // Act
        repository.delete(lastInsertedUser);
        User user = repository.find("id", lastInsertedUser.getId());

        // Assert
        assertEquals(null, user, "last must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns null if the no user was found
     * in the database response.
     */
    @Test
    @Order(10)
    public void testParseResponseFirst_ReturnNullIfNoUserWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        
        // Act
        User user = repository.parseResponseFirst(response);

        // Assert
        assertEquals(null, user, "User must be null");
    }

    /*
     * Method: parseResponseFirst(databaseResponse);
     * Test if the method returns a user object containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(11)
    public void testParseResponseFirst_ReturnUserIfUserWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestUserFactory.createUserRecord();
        response.add(record);

        // Authority list created from database record used to check 
        // if the database record's roles matches the returned user.
        LinkedList<SimpleGrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", (String) record.map().get("role_name"))));

        // Act
        User user = repository.parseResponseFirst(response);

        // Assert
        assertEquals((long) record.map().get("id"), user.getId(), "Ids must be equal");
        assertEquals((String) record.map().get("username"), user.getUsername(), "Usernames must be equal");
        assertEquals((String) record.map().get("password"), user.getPassword(), "Passwords must be equal");
        assertEquals((String) record.map().get("email"), user.getEmail(), "Emails must be equal");
        assertEquals(authorities, user.getAuthorities(), "authorities must be equal");
        assertEquals(((int) record.map().get("account_non_expired")) == 1, user.isAccountNonExpired(), "accountNonExpired must be equal");
        assertEquals(((int) record.map().get("account_non_locked")) == 1, user.isAccountNonLocked(), "accountNonLocked must be equal");
        assertEquals(((int) record.map().get("credentials_non_expired")) == 1, user.isCredentialsNonExpired(), "credentialsNonExpired must be equal");
        assertEquals(((int) record.map().get("enabled")) == 1, user.isEnabled(), "enabled must be equal");
    }

    /*
     * Method: parseResponse(databaseResponse);
     * Test if the method returns a list of user objects containing the same
     * data as the database record passed as argument.
     */
    @Test
    @Order(12)
    public void testParseResponse_ReturnUsersIfUsersWasFound() {
        // Arrange
        DatabaseResponse response = new DatabaseResponse();
        DatabaseRecord record = TestUserFactory.createUserRecord();
        response.add(record);

        // Authority list created from database record used to check 
        // if the database record's roles matches the returned user.
        LinkedList<SimpleGrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", (String) record.map().get("role_name"))));

        // Act
        List<User> users = repository.parseResponse(response);

        // Assert
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            assertEquals((long) record.map().get("id"), user.getId(), "Ids must be equal");
            assertEquals((String) record.map().get("username"), user.getUsername(), "Usernames must be equal");
            assertEquals((String) record.map().get("password"), user.getPassword(), "Passwords must be equal");
            assertEquals((String) record.map().get("email"), user.getEmail(), "Emails must be equal");
            assertEquals(authorities, user.getAuthorities(), "authorities must be equal");
            assertEquals(((int) record.map().get("account_non_expired")) == 1, user.isAccountNonExpired(), "accountNonExpired must be equal");
            assertEquals(((int) record.map().get("account_non_locked")) == 1, user.isAccountNonLocked(), "accountNonLocked must be equal");
            assertEquals(((int) record.map().get("credentials_non_expired")) == 1, user.isCredentialsNonExpired(), "credentialsNonExpired must be equal");
            assertEquals(((int) record.map().get("enabled")) == 1, user.isEnabled(), "enabled must be equal");
        }
    }
}