package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.ConfirmationToken;
import kz.silk.onlinebank.domain.model.Profile;
import org.springframework.lang.NonNull;

/**
 * Service for confirmation token send email
 *
 * @author YermukhanJJ
 */
public interface ConfirmationService {

    /**
     * Creating confirmation token
     *
     * @param profile {@link Profile} entity
     * @param ttlMinutes token total time
     * @return {@link ConfirmationToken} confirmation token
     * @throws BadRequestException bad request (400) exception
     */
    ConfirmationToken createToken(@NonNull Profile profile,
                                  @NonNull Integer ttlMinutes)
        throws BadRequestException;

    /**
     * confirm email address
     *
     * @param confirmationToken {@link ConfirmationToken} confirmation token entity
     * @throws NotFoundException not found (404) exception
     * @throws ForbiddenException forbidden (403) exception
     * @throws BadRequestException Bad request (400) exception
     */
    void confirmToken(@NonNull ConfirmationToken confirmationToken)
                    throws NotFoundException, ForbiddenException, BadRequestException;

    /**
     * Getting confirm token from db
     *
     * @param token confirmation token value
     * @return {@link ConfirmationToken} confirmation token entity
     * @throws NotFoundException not found (404) exception
     */
    ConfirmationToken getToken(@NonNull String token)
            throws NotFoundException;
}
