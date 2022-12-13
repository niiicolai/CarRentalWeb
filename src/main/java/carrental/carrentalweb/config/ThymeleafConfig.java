package carrental.carrentalweb.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

/*
 * Written by Nicolai Berg Andersen
 */
public class ThymeleafConfig {

    /*
     * Configure Spring Security dialect for Thymeleaf.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
