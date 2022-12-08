package carrental.carrentalweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import carrental.carrentalweb.services.UserService;

/*
 * Written by Nicolai Berg Andersen.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] employeePaths = {};
    private static final String employeeRole = "ROLE_EMPLOYEE";

    private static final String[] clientPaths = {};
    private static final String clientRole = "ROLE_CLIENT";

    /* Shared authories properties */
    private static final String[] sharedPaths = {"/user", "/credit/rating", "/pickuppoints**"};
    
    /* Unauthorized properties */
    private static final String[] unauthorizedPaths = {"/", "/cars", "/contact", "/pickups", "/subscriptions", "/signup", "/login**", "/css/**", "/js/**", "/images/**", "/css_framework"};


    private static final String LOGIN_PAGE = "/login";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?login_error=true";
    private static final String AFTER_LOGIN_URL = "/user";

    private static final String AFTER_LOGOUT_URL = "/";
    private static final String LOGOUT_URL = "/logout";
    
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
                .antMatchers(employeePaths).hasAuthority(employeeRole) 
                .antMatchers(clientPaths).hasAuthority(clientRole) 
                .antMatchers(sharedPaths).hasAnyAuthority(employeeRole, clientRole)
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .loginProcessingUrl(LOGIN_URL)
                .defaultSuccessUrl(AFTER_LOGIN_URL, true)
                .failureUrl(LOGIN_FAILURE_URL)
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessUrl(AFTER_LOGOUT_URL)
            .and()
            .csrf().disable()
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
    public UserDetailsService customUserDetailsService() {
        return new UserService();
    }
}
