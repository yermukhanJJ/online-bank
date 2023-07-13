package kz.silk.onlinebank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Unauthorized (401) response status exception
 *
 * @author YermukhanJJ
 */
public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
