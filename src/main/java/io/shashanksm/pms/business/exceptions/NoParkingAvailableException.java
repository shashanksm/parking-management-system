package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
public class NoParkingAvailableException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5453862400566070550L;

	public NoParkingAvailableException() {
		super("no parking slot available for incoming vehicle");
	}
}
