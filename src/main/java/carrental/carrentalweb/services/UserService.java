package carrental.carrentalweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import carrental.carrentalweb.repository.UserRepository;

/*
 * Written by Nicolai Berg Andersen.
 */


public class UserService implements UserDetailsService {

    private static final String USERNAME_COLUMN = "username";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   
        return userRepository.find(USERNAME_COLUMN, username);
    }
}
