package kz.silk.onlinebank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Internal error (500) response status exception
 *
 * @author YermukhanJJ
 */
public class InternalErrorException extends ResponseStatusException {

    public InternalErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
