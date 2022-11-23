package carrental.carrentalweb.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import carrental.carrentalweb.builder.UserBuilder;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.services.DatabaseService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Repository
public class UserRepository {

    @Autowired
    DatabaseService databaseService;

    public User find(String column, Object value) {
        String sql = String.format("SELECT * FROM users INNER JOIN roles ON users.id=roles.user_id WHERE users.%s=?", column);
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(value);
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, values);
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }
    
    public boolean insert(User user) {
        /* 
         * Ensure user password always is 
         * encoded before inserting 
         */
        user.encodedPassword();     

        String sql = "INSERT INTO users (username, password) (?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getUsername());
        values.add(user.getPassword());

        databaseService.executeUpdate(sql, values);

        return true;
    }

    public boolean update(User user) {
        /* 
         * Password encoding is not needed, 
         * because the password is not updated
         */        
        String sql = "UPDATE users SET username = ?";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getUsername());

        databaseService.executeUpdate(sql, values);
        
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

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getPassword());

        databaseService.executeUpdate(sql, values);
        
        return true;
    }

    public boolean delete(User user) {
        String sql = "DELETE users WHERE id = ?";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getPassword());

        databaseService.executeUpdate(sql, values);
        
        return true;
    }

    public User last() {
        String sql = String.format("SELECT * FROM users ORDER BY created_at DESC LIMIT 1");
        
        List<HashMap<String, Object>> resultList = databaseService.executeQuery(sql, new LinkedList<>());
        if (resultList == null) return null;

        return parseFromMap(resultList.get(0));
    }

    private User parseFromMap(HashMap<String, Object> map) {
        if (map == null) return null;
        return new UserBuilder()
            .id((int) map.get("id"))
            .username((String) map.get("username"))
            .password((String) map.get("password"))
            .roles("CLIENT", "EMPLOYEE")
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();
    }
}
