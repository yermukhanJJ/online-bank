package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.config.properties.ConfirmationProperties;
import kz.silk.onlinebank.domain.dto.request.LoginRequestDto;
import kz.silk.onlinebank.domain.dto.request.RegisterRequestDto;
import kz.silk.onlinebank.domain.dto.response.LoginResponseDto;
import kz.silk.onlinebank.domain.enums.EmailTemplates;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.InternalErrorException;
import kz.silk.onlinebank.domain.model.ConfirmationToken;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.service.AuthService;
import kz.silk.onlinebank.service.ConfirmationService;
import kz.silk.onlinebank.service.EmailService;
import kz.silk.onlinebank.service.ProfileService;
import kz.silk.onlinebank.utils.jwt.jwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service(value = AuthServiceImpl.SERVICE_VALUE)
public class AuthServiceImpl implements AuthService {

    public static final String SERVICE_VALUE = "AuthServiceImpl";

    private final ProfileService profileService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final jwtUtils jwtUtils;
    private final ConfirmationService confirmationService;
    private final ConfirmationProperties confirmationProperties;

    /**
     * Registration
     *
     * @param requestDto registration data
     */
    @Transactional
    @Override
    public void register(@NonNull RegisterRequestDto requestDto) {
        log.info("register({})", requestDto);

        Profile profile = profileService.createProfile(
                requestDto.getEmail(),
                requestDto.getUsername(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getPassword()
        );

        ConfirmationToken confirmationToken =
                confirmationService.createToken(profile, confirmationProperties.getToken().getTtl());

        sendConfirmationEmail(confirmationToken, false);
    }

    /**
     * get token
     *
     * @param requestDto Login data
     * @return {@link LoginResponseDto} jwt object
     */
    @Override
    public LoginResponseDto login(@NonNull LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponseDto(jwtUtils.generateJwtToken(authentication));
    }

    @Override
    public void confirmRegistration(@NonNull String email,
                                    @NonNull String token) throws BadRequestException {
        log.info("confirmRegistration({}, {})", email, token);
        ConfirmationToken confirmationToken = confirmationService.getToken(token);

        // Check if addresses match
        if (!confirmationToken.getProfile().getEmail().equals(email)) {
            throw new BadRequestException("Email addresses don't match");
        }

        try {
            confirmationService.confirmToken(confirmationToken);
        } catch (ForbiddenException e) {
            Profile profile = confirmationToken.getProfile();

            // Create and save new confirmation token
            ConfirmationToken newConfirmationToken =
                    confirmationService.createToken(profile, confirmationProperties.getToken().getTtl() * 2);

            // Resend confirmation email
            sendConfirmationEmail(newConfirmationToken, true);

            throw e;
        }
        profileService.updateEmailConfirmationStatus(confirmationToken.getProfile().getId(), true);
    }

    /**
     * Sends an email with confirmation token link.
     *
     * @param confirmationToken {@link ConfirmationToken} object
     * @throws InternalErrorException Email sending internal error
     */
    private void sendConfirmationEmail(@NonNull ConfirmationToken confirmationToken,
                                       boolean resend) throws InternalErrorException {
        try {
            String emailText = emailService.formText(
                    resend ? EmailTemplates.EMAIL_CONFIRMATION_RESEND : EmailTemplates.EMAIL_CONFIRMATION,
                    confirmationToken.getProfile().getFirstName(),
                    buildConfirmationLink(confirmationProperties.getUrl(), confirmationToken.getProfile().getEmail(),
                            confirmationToken.getToken()),
                    confirmationProperties.getToken().getTtl()
            );
            emailService.send(confirmationToken.getProfile().getEmail(),
                    confirmationProperties.getEmailSubject(), emailText, true);
        } catch (IllegalArgumentException | IOException exception) {
            throw new InternalErrorException("Failed to send confirmation email");
        }
    }

    /**
     * Builds confirmation token URL.
     *
     * @param httpUrl Confirmation link HTTP URL
     * @param emailParam Email address
     * @param tokenParam Confirmation token parameter
     * @return String value of confirmation link
     */
    private static String buildConfirmationLink(@NonNull String httpUrl,
                                                @NonNull String emailParam,
                                                @NonNull String tokenParam) {
        return UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("email", emailParam)
                .queryParam("token", tokenParam)
                .build()
                .toUriString();
    }
}
