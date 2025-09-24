package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8781728748363477151L;
	
	public ParkingNotFoundException() {
		super("parking does not exist with given details");
	}
	
}
