package carrental.carrentalweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import carrental.carrentalweb.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* Client properties */
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String[] CLIENT_PATHS = {"/"};

    /* Employee properties */
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    private static final String[] EMPLOYEE_PATHS = {"/"};
    
    /* Unauthorized properties */
    private static final String[] unauthorizedPaths = {"/"};

    /* Redirects */
    private static final String AFTER_LOGIN_URL = "/user";
    private static final String AFTER_LOGOUT_URL = "/";

    /* Encryption */
    public static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /* 
     *  Configure authorization on incoming HTTP request.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(unauthorizedPaths)
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers(CLIENT_PATHS).hasRole(CLIENT_ROLE)
                .antMatchers(EMPLOYEE_PATHS).hasRole(EMPLOYEE_ROLE)
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .defaultSuccessUrl(AFTER_LOGIN_URL, true)
            .and()
                .logout()
                .logoutSuccessUrl(AFTER_LOGOUT_URL)
            .and()
                .httpBasic();
        return http.build();
    }

    /* 
     *  Set password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() { 
        return ENCODER; 
    }

    /* 
     *  Set user service.
     */
    @Bean
    public UserService customUserDetailsService() {
        return new UserService();
    }
}
