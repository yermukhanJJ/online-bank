package kz.silk.onlinebank.domain.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Authentication request DTO
 *
 * @author YermukhanJJ
 */
@Data
public class LoginRequestDto {

    /**
     * User name
     */
    @NotBlank(message = "Username is required.")
    private String username;

    /**
     * User password
     */
    @ToString.Exclude
    @NotBlank(message = "Password is required")
    private String password;
}
