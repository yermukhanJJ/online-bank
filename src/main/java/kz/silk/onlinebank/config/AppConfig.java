package kz.silk.onlinebank.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Application config class
 *
 * @author YermukhanJJ
 */
@Configuration
@ConfigurationPropertiesScan("kz.silk.onlinebank.config.properties")
public class AppConfig {

    /**
     * Password encoder bean
     *
     * @return {@link BCryptPasswordEncoder} object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
