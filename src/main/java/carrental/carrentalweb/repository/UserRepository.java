package carrental.carrentalweb.repository;

import org.springframework.stereotype.Repository;

import carrental.carrentalweb.builder.UserBuilder;
import carrental.carrentalweb.entities.User;

@Repository
public class UserRepository {

    public User find(String column, Object value) {
        String sql = String.format("SELECT * FROM users INNER JOIN roles ON users.id=roles.user_id WHERE users.%s=?", column);
        
        /* TODO: Fetch user from database */

        return new UserBuilder()
            .id(1)
            .username("username")
            .password("12345678")
            .encodedPassword() // Only required if the password is not encoded.
            .roles("CLIENT", "EMPLOYEE")
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();
    }
    
    public boolean insert(User user) {
        /* 
         * Ensure user password always is 
         * encoded before inserting 
         */
        user.encodedPassword();        
        String sql = "INSERT INTO users (username, password) (?, ?)";

        /* TODO: Insert user into database */

        return true;
    }

    public boolean update(User user) {
        /* 
         * Password encoding is not needed, 
         * because the password is not update 
         */        
        String sql = "UPDATE users SET username = ?";

        /* TODO: Modify the database user */
        
        return true;
    }

    /* 
     * A seperate method to update password to avoid the user
     * having to update their password when they want to change
     * their name or something else.
     */
    public boolean updatePassword(User user) {
        /* Ensure user passwords always is encoded before updating */
        user.encodedPassword();
        String sql = "UPDATE users SET password = ?";

        /* TODO: Modify the database user */
        
        return true;
    }

    public boolean delete(User user) {
        String sql = "DELETE users WHERE id = ?";

        /* TODO: Delete from the database */
        
        return true;
    }
}
