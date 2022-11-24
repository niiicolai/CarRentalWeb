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
        String sql = String.format("SELECT * FROM users INNER JOIN user_role ON users.id=user_role.user_id WHERE users.%s = ? ", column);
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

        String sql = "INSERT INTO users (username, password, email, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?, ?)";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getUsername());
        values.add(user.getPassword());
        values.add(user.getEmail());
        values.add(true);
        values.add(true);
        values.add(true);
        values.add(true);

        databaseService.executeUpdate(sql, values);

        User last = last();
        String sql2 = "INSERT INTO user_role (role_name, user_id) VALUES (?, ?)";
        LinkedList<Object> values2 = new LinkedList<>();
        values2.add("CLIENT");
        values2.add(last.getId());

        databaseService.executeUpdate(sql2, values2);

        return true;
    }

    public boolean update(User user) {
        /* 
         * Password encoding is not needed, 
         * because the password is not updated
         */        
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        
        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getUsername());
        values.add(user.getEmail());
        values.add(user.getId());

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
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getPassword());
        values.add(user.getId());

        databaseService.executeUpdate(sql, values);
        
        return true;
    }

    public boolean disable(User user) {
        String sql = "UPDATE users SET enabled = 0 WHERE id = ?";

        LinkedList<Object> values = new LinkedList<>();
        values.add(user.getId());

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
            .id((long) map.get("id"))
            .username((String) map.get("username"))
            .password((String) map.get("password"))
            .email((String) map.get("email"))
            .roles((String) map.get("role_name"))
            .isAccountNonExpired((int) map.get("account_non_expired") == 1)
            .isAccountNonLocked((int) map.get("account_non_locked") == 1)
            .isCredentialsNonExpired((int) map.get("credentials_non_expired") == 1)
            .isEnabled((int) map.get("enabled") == 1)
            .build();
    }
}
