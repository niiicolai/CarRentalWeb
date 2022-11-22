package carrental.carrentalweb.repository;

import org.springframework.beans.factory.annotation.Value;

// Mads
public class SubscriptionRepository {
    @Value("${JDBCUrl}")
    private String url;
    @Value("${JDBCUsername")
    private String username;
    @Value("${JDBCPassword")
    private String password;

    public SubscriptionRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
