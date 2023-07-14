package kz.silk.onlinebank.config;

import kz.silk.onlinebank.domain.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerBadRequestException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getStatus()) {
            case BAD_REQUEST -> errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
            case FORBIDDEN -> errorResponse.setErrorCode(HttpStatus.FORBIDDEN.value());
            case NOT_FOUND -> errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
            case INTERNAL_SERVER_ERROR -> errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            case UNAUTHORIZED -> errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        }
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }
}
