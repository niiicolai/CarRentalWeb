package carrental.carrentalweb.entity_factories;

import java.time.LocalDateTime;
import java.util.HashMap;

import carrental.carrentalweb.builder.UserBuilder;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.records.DatabaseRecord;

public class TestUserFactory {

    /*
     * Creates a test user with random data that
     * can be used in Unit and integration tests.
     */
    public static User create() {
        UserBuilder builder = new UserBuilder();
        String time = LocalDateTime.now().toString();
        String email = String.format("%s@%s.%s", time, time, time);
        return builder
            .username(time)
            .password(time)
            .email(email)
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .roles("CLIENT")
            .build();
    }

    /*
     * Creates a database record with random data
     * matching the data a user is made of in the database.
     * The method can be used in Unit and integration tests.
     */
    public static DatabaseRecord createUserRecord() {
        String time = LocalDateTime.now().toString();
        String email = String.format("%s@%s.%s", time, time, time);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("username", time);
        map.put("password", time);
        map.put("email", email);
        map.put("role_name", "CLIENT");
        map.put("created_at", LocalDateTime.now());
        map.put("updated_at", LocalDateTime.now());
        map.put("account_non_expired", 1);
        map.put("account_non_locked", 1);
        map.put("credentials_non_expired", 1);
        map.put("enabled", 1);
        return new DatabaseRecord(map);
    }

}
