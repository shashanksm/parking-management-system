package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate that a vehicle is already parked.
 * This exception is annotated with `@ResponseStatus` to return an HTTP 409 Conflict status.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class VehicleAlreadyParkedException extends RuntimeException {

    public VehicleAlreadyParkedException(String message) {
        super(message);
    }
}
