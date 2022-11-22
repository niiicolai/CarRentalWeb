package carrental.carrentalweb.builder;

import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import carrental.carrentalweb.config.SecurityConfig;
import carrental.carrentalweb.entities.User;

public class UserBuilder {

    private static final String ROLE_PREFIX = "ROLE_%s";
    
    private User user;

    public UserBuilder () {
        this.user = new User();
    }

    public UserBuilder id(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder username(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder encodedPassword() {
        user.encodedPassword();
        return this;
    }

    public UserBuilder roles(String... roles) {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles)
            authorities.add(new SimpleGrantedAuthority(String.format(ROLE_PREFIX, role)));
        user.setAuthorities(authorities);
        return this;
    }

    public UserBuilder isAccountNonExpired(boolean accountNonExpired) {
        user.setAccountNonExpired(accountNonExpired);
        return this;
    }

    public UserBuilder isAccountNonLocked(boolean accountNonLocked) {
        user.setAccountNonLocked(accountNonLocked);
        return this;
    }

    public UserBuilder isCredentialsNonExpired(boolean credentialsNonExpired) {
        user.setCredentialsNonExpired(credentialsNonExpired);
        return this;
    }

    public UserBuilder isEnabled(boolean enabled) {
        user.setEnabled(enabled);
        return this;
    }

    public User build() {
        return user;
    }
}
