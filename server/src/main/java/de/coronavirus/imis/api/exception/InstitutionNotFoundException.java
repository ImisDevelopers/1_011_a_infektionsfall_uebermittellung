package de.coronavirus.imis.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InstitutionNotFoundException extends RuntimeException {
    public InstitutionNotFoundException(final String message) {
        super(message);
    }
}
