package carrental.carrentalweb.repository;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import carrental.carrentalweb.builder.UserBuilder;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.records.DatabaseRecord;
import carrental.carrentalweb.services.DatabaseService;
import carrental.carrentalweb.utilities.DatabaseRequestBody;
import carrental.carrentalweb.utilities.DatabaseResponse;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class UserRepository {

    private final DatabaseService databaseService;

    public UserRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public User find(String column, Object value) {
        String sql = String.format("SELECT * FROM users INNER JOIN user_role ON users.id=user_role.user_id WHERE users.%s = ? ", column);
        DatabaseRequestBody body = new DatabaseRequestBody(value);
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, body);
        
        return parseResponse(databaseResponse).get(0);
    }
    
    public boolean insert(User user) {
        /* 
         * Ensure user password always is 
         * encoded before inserting 
         */
        user.encodedPassword();  

        String userSql = "INSERT INTO users (username, password, email, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_role (role_name, user_id) VALUES (?, ?)";

        DatabaseRequestBody userRequestbody = new DatabaseRequestBody(user.getUsername(), user.getPassword(), 
            user.getEmail(), true, true, true, true);
        DatabaseRequestBody roleRequestbody = new DatabaseRequestBody("CLIENT", last().getId());
        
        DatabaseResponse databaseResponse = databaseService.executeUpdate(userSql, userRequestbody);
        databaseService.executeUpdate(roleSql, roleRequestbody);

        return databaseResponse.isSuccessful();
    }

    public boolean update(User user) {
        /* 
         * Password encoding is not needed, 
         * because the password is not updated
         */        
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        DatabaseRequestBody body = new DatabaseRequestBody(user.getUsername(), user.getEmail(), user.getId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        
        return databaseResponse.isSuccessful();
    }

    /* 
     * A seperate method to update password to avoid the user
     * having to update their password when they want to change
     * their name or something else.
     */
    public boolean updatePassword(User user) {
        /* Ensure user passwords always is encoded before updating */
        user.encodedPassword();

        String sql = "UPDATE users SET password = ? WHERE id = ?";
        DatabaseRequestBody body = new DatabaseRequestBody(user.getPassword(), user.getId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        
        return databaseResponse.isSuccessful();
    }

    public boolean disable(User user) {
        String sql = "UPDATE users SET enabled = 0 WHERE id = ?";
        DatabaseRequestBody body = new DatabaseRequestBody(user.getId());
        DatabaseResponse databaseResponse = databaseService.executeUpdate(sql, body);
        
        return databaseResponse.isSuccessful();
    }

    public User last() {
        String sql = "SELECT * FROM users ORDER BY created_at DESC LIMIT 1";
        DatabaseResponse databaseResponse = databaseService.executeQuery(sql, new DatabaseRequestBody());
        return parseResponse(databaseResponse).get(0);
    }

    private List<User> parseResponse(DatabaseResponse databaseResponse) {
        List<User> users = new LinkedList<User>();
        while (databaseResponse.hasNext()) {
            DatabaseRecord record = databaseResponse.next();

            users.add(
                new UserBuilder()
                    .id((long) record.map().get("id"))
                    .username((String) record.map().get("username"))
                    .password((String) record.map().get("password"))
                    .email((String) record.map().get("email"))
                    .roles((String) record.map().get("role_name"))
                    .isAccountNonExpired((int) record.map().get("account_non_expired") == 1)
                    .isAccountNonLocked((int) record.map().get("account_non_locked") == 1)
                    .isCredentialsNonExpired((int) record.map().get("credentials_non_expired") == 1)
                    .isEnabled((int) record.map().get("enabled") == 1)
                    .build()
            );
        }

        return users;
    }
}
