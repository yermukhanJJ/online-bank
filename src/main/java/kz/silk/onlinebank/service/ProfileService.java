package kz.silk.onlinebank.service;

import kz.silk.onlinebank.domain.dto.request.ProfileUpdateDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.exceptions.UnauthorizedException;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.domain.model.Role;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService extends UserDetailsService {

    /**
     * Creates new user profile
     *
     * @param email Email address
     * @param username User name
     * @param firstName First name
     * @param lastName last name
     * @param password password
     * @return Created {@link Profile} entity
     * @throws BadRequestException User with provided email already exists
     */
    Profile createProfile(@NonNull String email,
                          @NonNull String username,
                          @NonNull String firstName,
                          @NonNull String lastName,
                          @NonNull String password) throws BadRequestException;


    /**
     * Returns a profile of current user
     *
     * @return Fetched {@link Profile} entity
     * @throws UnauthorizedException Unauthorized
     */
    Profile getCurrentProfile() throws UnauthorizedException;

    /**
     * Returns a profile by ID
     *
     * @param id Profile ID
     * @return Fetched {@link Profile} entity
     * @throws NotFoundException Profile not found
     */
    Profile getProfile(@NonNull Long id) throws NotFoundException;

    /**
     * Returns a profile by email
     *
     * @param email Email address
     * @return Fetched {@link Profile} entit
     * @throws NotFoundException Profile not found
     */
    Profile getProfile(@NonNull String email) throws NotFoundException;

    /**
     * Returns a profile by username
     *
     * @param username User name
     * @return Fetched {@link Profile} entity
     * @throws NotFoundException Profile not found
     */
    Profile getProfileByUsername(@NonNull String username) throws NotFoundException;

    /**
     * Updates current user profile
     *
     * @param profileUpdateDto User profile new data
     */
    void updateCurrentProfile(ProfileUpdateDto profileUpdateDto);

    /**
     * Updates user profiles role by profile ID.
     * @param id Profile ID
     * @param role New user role
     * @throws ForbiddenException Not allowed to change the role
     */
    void updateProfileRole(@NonNull Long id,
                           @NonNull String role) throws ForbiddenException;

    /**
     * Updates email confirmation status.
     *
     * @param emailConfirmed Email confirmation new status
     * @throws NotFoundException User doesn't exist
     */
    void updateEmailConfirmationStatus(@NonNull Long id,
                                       boolean emailConfirmed) throws NotFoundException;

    /**
     * Updates profile lockout status.
     *
     * @param blocked Profile lockout new status
     * @throws NotFoundException User doesn't exist
     */
    void updateLockoutStatus(@NonNull Long id,
                             boolean blocked) throws NotFoundException;
}
