package kz.silk.onlinebank.domain.dto.response;

import kz.silk.onlinebank.domain.model.Profile;
import kz.silk.onlinebank.domain.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * User profile DTO
 *
 * @author YermukhanJJ
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProfileDto {

    /**
     * Profile ID
     */
    private Long id;

    /**
     * Profile email
     */
    private String email;

    /**
     * User first name
     */
    private String firstName;

    /**
     * User last name
     */
    private String lastName;

    /**
     * User Roles
     */
    private Set<String> roles;

    /**
     * Creates user profile dto from entity object
     *
     * @param profile Profile entity
     * @return Created {@link ProfileDto} object
     */
    public static ProfileDto fromEntity(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setEmail(profile.getEmail());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setRoles(
                profile.getRoles().stream().map(
                        Role::getTitle
                ).collect(Collectors.toSet())
        );

        return profileDto;
    }
}
