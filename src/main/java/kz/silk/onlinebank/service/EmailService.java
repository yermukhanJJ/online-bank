package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.enums.EmailTemplates;
import kz.silk.onlinebank.domain.exceptions.InternalErrorException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service interface with email processing methods.
 *
 * @author YermukhanJJ
 */
@Service
public interface EmailService {

    /**
     * Sends an email.
     *
     * @param receiver Receiver email address
     * @param subject Email subject
     * @param text Email content
     * @throws InternalErrorException Failed to send email
     */
    void send(@NonNull String receiver,
              @NonNull String subject,
              @NonNull String text,
              boolean html) throws InternalErrorException;

    /**
     * Forms email text from template.
     *
     * @param template Template value
     * @param args Template arguments
     * @return Formed email text string value
     * @throws IllegalArgumentException Invalid arguments count
     * @throws IOException Unable to read template file
     */
    String formText(@NonNull EmailTemplates template,
                    Object... args) throws IllegalArgumentException, IOException;
}
