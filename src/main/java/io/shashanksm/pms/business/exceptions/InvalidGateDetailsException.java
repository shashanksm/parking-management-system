package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidGateDetailsException extends RuntimeException{
	public InvalidGateDetailsException(String msg) {
		super(msg);
	}
}
