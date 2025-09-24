package io.shashanksm.pms.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoPricingRuleFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6450253534016411489L;
	
	public NoPricingRuleFoundException() {
		super("PricingRule could not be found in database");
	}

}
