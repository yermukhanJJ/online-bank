package kz.silk.onlinebank.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kz.silk.online.bank.token")
public class JwtProperties {

    /**
     * token secret
     */
    private String secret;

    /**
     * Token Expiration time
     */
    private int expirationMs;
}
