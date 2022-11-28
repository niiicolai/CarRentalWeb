package carrental.carrentalweb.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import carrental.carrentalweb.entities.User;
import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen.
 */


public class UserService implements UserDetailsService {

    private static final String USERNAME_COLUMN = "username";

    private UserRepository userRepository;

    public UserService () {
    }

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.find(USERNAME_COLUMN, username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        
        return user; 
    }
}
