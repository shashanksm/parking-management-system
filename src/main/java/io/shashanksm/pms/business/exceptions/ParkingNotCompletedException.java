package io.shashanksm.pms.business.exceptions;

public class ParkingNotCompletedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901125715307945741L;
	private String hint;

	public ParkingNotCompletedException() {
		super("parking is not yet complete");
		this.hint = "operation may require completedAt property to be valid date";
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
