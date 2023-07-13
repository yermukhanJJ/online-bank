package kz.silk.onlinebank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Forbidden (403) response status exception
 *
 * @author YermukhanJJ
 */
public class ForbiddenException extends ResponseStatusException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
