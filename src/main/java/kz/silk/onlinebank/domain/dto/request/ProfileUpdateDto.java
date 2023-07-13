package kz.silk.onlinebank.domain.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ProfileUpdateDto {

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


    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
