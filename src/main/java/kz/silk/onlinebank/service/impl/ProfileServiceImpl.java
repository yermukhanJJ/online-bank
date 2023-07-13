package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.domain.dao.ProfileRepository;
import kz.silk.onlinebank.domain.dto.request.ProfileUpdateDto;
import kz.silk.onlinebank.domain.exceptions.BadRequestException;
import kz.silk.onlinebank.domain.exceptions.ForbiddenException;
import kz.silk.onlinebank.domain.exceptions.NotFoundException;
import kz.silk.onlinebank.domain.exceptions.UnauthorizedException;
import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.domain.model.Role;
import kz.silk.onlinebank.service.ProfileService;
import kz.silk.onlinebank.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service(value = ProfileServiceImpl.SERVICE_VALUE)
public class ProfileServiceImpl implements ProfileService {

    public static final String SERVICE_VALUE = "ProfileServiceImpl";

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public Profile createProfile(@NonNull String email,
                                 @NonNull String username,
                                 @NonNull String firstName,
                                 @NonNull String lastName,
                                 @NonNull String password) throws BadRequestException {
        log.debug("createProfile({}, ..., {}, {}, {})", email, username, firstName, lastName);
        Profile profile = new Profile();

        if (profileRepository.existsByEmail(email)) {
            throw new BadRequestException(String.format("User with email %s already exists", email));
        }

        if (profileRepository.existsByUsername(username)) {
            throw new BadRequestException(String.format("User with username %s already exists", username));
        }
        profile.setEmail(email.toLowerCase());
        profile.setUsername(username);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setPassword(passwordEncoder.encode(password));

        Role role = roleService.getRoleByTitle(ROLE_USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        profile.setRoles(roles);

        return profileRepository.save(profile);
    }

    @Override
    public Profile getCurrentProfile() throws UnauthorizedException {
        log.debug("getCurrentProfile()");
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authenticationToken == null) {
            throw new UnauthorizedException();
        }
        return getProfileByUsername(authenticationToken.getName());
    }

    @Override
    public Profile getProfile(@NonNull Long id) throws NotFoundException {
        log.debug("getProfile({})", id);
        return profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Profile getProfile(@NonNull String email) throws NotFoundException {
        log.debug("getProfile({})", email);
        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public Profile getProfileByUsername(@NonNull String username) throws NotFoundException {
        log.debug("getProfileByUsername({})", username);
        try {
            return (Profile) loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    //TODO: оптимизировать метод
    @Override
    public void updateCurrentProfile(ProfileUpdateDto profileUpdateDto) {
        log.debug("updateCurrentProfile({})", profileUpdateDto);
        Profile profile = getCurrentProfile();
        if (profileUpdateDto.getUsername() != null && !profile.getUsername().equals(profileUpdateDto.getUsername())) {
            if (profileRepository.existsByUsername(profileUpdateDto.getUsername())) {
                throw new BadRequestException(String.format("User with username %s already exists", profileUpdateDto.getUsername()));
            } else {
                profile.setUsername(profileUpdateDto.getUsername());
            }
        } else {
            profile.setUsername(profileUpdateDto.getUsername() != null && !profileUpdateDto.getUsername().isEmpty() ? profileUpdateDto.getUsername() : profile.getUsername());
        }

        if (profileUpdateDto.getEmail() != null && !profile.getEmail().equals(profileUpdateDto.getEmail())) {
            if (profileRepository.existsByEmail(profileUpdateDto.getEmail())) {
                throw new BadRequestException(String.format("User with email %s already exists", profileUpdateDto.getEmail()));
            } else {
                profile.setEmail(profileUpdateDto.getEmail());
            }
        } else {
            profile.setEmail(profileUpdateDto.getEmail() != null && !profileUpdateDto.getEmail().isEmpty() ? profileUpdateDto.getEmail() : profile.getEmail());
        }

        profile.setFirstName(profileUpdateDto.getFirstName() != null && !profileUpdateDto.getFirstName().isEmpty() ? profileUpdateDto.getFirstName() : profile.getFirstName());
        profile.setLastName(profileUpdateDto.getLastName() != null && !profileUpdateDto.getLastName().isEmpty() ? profileUpdateDto.getLastName() : profile.getLastName());

        profileRepository.save(profile);
    }

    @Override
    public void updateProfileRole(@NonNull Long id, @NonNull String role) throws ForbiddenException {
        log.debug("updateProfileRole({}, {})", id, role);
        if (getCurrentProfile().getRoles().contains(roleService.getRoleByTitle(ROLE_ADMIN))) {
            Profile profile = getProfile(id);
            Role newRole = roleService.getRoleByTitle(role);
            profile.getRoles().add(newRole);
            profileRepository.save(profile);
        } else {
            throw new ForbiddenException("Not allowed to change admin roles");
        }
    }

    @Override
    public void updateEmailConfirmationStatus(@NonNull Long id, boolean emailConfirmed) throws NotFoundException {
        log.debug("updateEmailConfirmationStatus({}, {})", id, emailConfirmed);
        Profile profile = getProfile(id);
        profile.setEmailConfirmed(emailConfirmed);
        profileRepository.save(profile);
    }

    @Override
    public void updateLockoutStatus(@NonNull Long id, boolean blocked) throws NotFoundException {
        log.debug("updateLockoutStatus({}, {})", id, blocked);
        if (getCurrentProfile().getRoles().contains(roleService.getRoleByTitle(ROLE_ADMIN))) {
            Profile profile = getProfile(id);
            profile.setBlocked(blocked);
        } else {
            throw new ForbiddenException("Not allowed to change user data");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername({})", username);
        Optional<? extends UserDetails> userDetails = profileRepository.findByUsername(username);
        if (!userDetails.isPresent()) {
            throw new UsernameNotFoundException("User with username %s not found");
        }
        return userDetails.get();
    }
}
