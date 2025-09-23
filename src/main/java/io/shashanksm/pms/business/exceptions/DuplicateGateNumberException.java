package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateGateNumberException extends RuntimeException {

    public DuplicateGateNumberException(String message) {
        super(message);
    }
}