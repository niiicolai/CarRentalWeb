package carrental.carrentalweb.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.builder.UserBuilder;
import carrental.carrentalweb.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final String USERNAME_COLUMN = "username";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {        
        UserRepository repository = new UserRepository();    
        return repository.find(USERNAME_COLUMN, username);
    }
}
