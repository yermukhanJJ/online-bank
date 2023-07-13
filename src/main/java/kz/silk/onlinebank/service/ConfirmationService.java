package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.ConfirmationToken;
import kz.silk.onlinebank.domain.model.Profile;
import org.springframework.lang.NonNull;

public interface ConfirmationService {

    ConfirmationToken createToken(@NonNull Profile profile,
                                  @NonNull Integer ttlMinutes)
        throws BadRequestException;

    void confirmToken(@NonNull ConfirmationToken confirmationToken)
                    throws NotFoundException, ForbiddenException, BadRequestException;

    ConfirmationToken getToken(@NonNull String token)
            throws NotFoundException;
}
