package kz.silk.onlinebank.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Login response DTO
 *
 * @author YermukhanJJ
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginResponseDto {

    /**
     * Authorization JWT value
     */
    private String authToken;
}
