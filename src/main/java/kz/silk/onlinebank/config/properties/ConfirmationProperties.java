package kz.silk.onlinebank.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kz.silk.online.bank.confirmation")
public class ConfirmationProperties {

    /**
     * Confirmation url
     */
    private String url;

    /**
     * Email subject
     */
    private String emailSubject;

    /**
     * Token properties
     */
    private Token token;

    /**
     * Confirmation token
     */
    @Getter
    @Setter
    public static class Token {

        /**
         * Token expiration ms
         */
        private Integer ttl;
    }

}
