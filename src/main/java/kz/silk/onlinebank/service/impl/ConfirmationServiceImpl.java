package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.ConfirmationRepository;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.model.ConfirmationToken;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service(value = ConfirmationServiceImpl.SERVICE_VALUE)
public class ConfirmationServiceImpl implements ConfirmationService {

    public static final String SERVICE_VALUE = "ConfirmationServiceImpl";

    private final ConfirmationRepository confirmationRepository;

    @Override
    public void confirmToken(@NonNull ConfirmationToken confirmationToken) throws NotFoundException, ForbiddenException, BadRequestException {
        log.debug("confirmToken({})", confirmationToken);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new BadRequestException("Confirmation token has already been used");
        }
        else if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ForbiddenException("Confirmation token has been expired");
        }

        if (confirmationRepository.updateConfirmedAt(confirmationToken.getToken(), LocalDateTime.now()) < 1) {
            throw new NotFoundException();
        }
    }

    @Override
    public ConfirmationToken getToken(@NonNull String token) throws NotFoundException {
        return confirmationRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException(String.format("Token %s not found", token)));
    }

    @Override
    public ConfirmationToken createToken(@NonNull Profile profile,
                                         @NonNull Integer ttlMinutes) throws BadRequestException {
        log.debug("createToken({})", profile);
        if (profile.getEmailConfirmed()) {
            throw new BadRequestException(
                    String.format("User email %s is already confirmed", profile.getEmail()));
        }
        return confirmationRepository.save(
                new ConfirmationToken(
                        UUID.randomUUID().toString(),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(ttlMinutes),
                        profile
                )
        );
    }
}
