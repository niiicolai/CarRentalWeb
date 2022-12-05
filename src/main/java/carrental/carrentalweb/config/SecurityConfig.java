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

    /* Client properties */
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String[] CLIENT_PATHS = {"/user", "/credit/rating", "/pickuppoints**"};

    /* Employee properties */
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    private static final String[] EMPLOYEE_PATHS = {"/user"};
    
    /* Unauthorized properties */
    private static final String[] unauthorizedPaths = {"/", "/signup", "/login**", "/css/**", "/images/**", "/css_framework"};


    private static final String LOGIN_PAGE = "/login";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error=true";
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
                .antMatchers(CLIENT_PATHS).hasRole(CLIENT_ROLE)
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
