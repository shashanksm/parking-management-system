package io.shashanksm.pms.business.exceptions;

public class NoPricingRuleFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6450253534016411489L;
	
	public NoPricingRuleFoundException() {
		super("PricingRule could not be found in database");
	}

}
