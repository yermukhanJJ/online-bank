package kz.silk.onlinebank.domain.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Registration request DTO
 *
 * @author YermukhanJJ
 */
@Data
public class RegisterRequestDto {

    /**
     * User name
     */
    @NotBlank(message = "User name is required")
    private String username;

    /**
     * First name
     */
    @NotBlank(message = "First name is required")
    private String firstName;

    /**
     * Last name
     */
    @NotBlank(message = "Last name is required")
    private String lastName;

    /**
     * User email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address", flags = { Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    /**
     * User password
     */
    @ToString.Exclude
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password length must be greater than 8")
    private String password;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}
