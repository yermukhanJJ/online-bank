package kz.silk.onlinebank.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kz.silk.online.bank.email")
public class EmailProperties {

    /**
     * Email from
     */
    private String emailFrom;

    /**
     * Template files directory
     */
    private String templatesLocation;
}
