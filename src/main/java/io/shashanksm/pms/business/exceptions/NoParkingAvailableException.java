package io.shashanksm.pms.business.exceptions;

public class NoParkingAvailableException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5453862400566070550L;

	public NoParkingAvailableException() {
		super("no parking slot available for incoming vehicle");
	}
}
