package kz.silk.onlinebank.domain.dto.response;

import lombok.Data;

/**
 * Exception data
 *
 * @author YermukhanJJ
 */
@Data
public class ErrorResponse {

    private int errorCode;

    private String message;
}
