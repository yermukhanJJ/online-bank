package kz.silk.onlinebank.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Bad request  (400) response status exception
 *
 * @author YermukhanJJ
 */
public class BadRequestException extends ResponseStatusException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
