package io.shashanksm.pms.business;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.shashanksm.pms.business.exceptions.ParkingNotCompletedException;
import io.shashanksm.pms.dtos.ParkingDto;
import io.shashanksm.pms.dtos.PricingRuleDto;

@Component
public class StandardRuleBasedPriceCalculator implements PriceCalculator {

	private static final Logger log = LoggerFactory.getLogger(StandardRuleBasedPriceCalculator.class);

	private PricingRuleDto pricingRuleDto;

	public StandardRuleBasedPriceCalculator(PricingRuleDto pricingRuleDto) {
		super();
		this.pricingRuleDto = pricingRuleDto;
	}

	public StandardRuleBasedPriceCalculator() {
		super();
	}

	public PricingRuleDto getPricingRuleDto() {
		return pricingRuleDto;
	}

	public void setPricingRuleDto(PricingRuleDto pricingRuleDto) {
		this.pricingRuleDto = pricingRuleDto;
	}

	@Override
	public Double calculatePrice(ParkingDto parkingDto) {
		
		//check if parking is complete
		if(parkingDto.completedAt() == null)
			throw new ParkingNotCompletedException();

		Instant entryTime = parkingDto.createdAt();
		Instant exitTime = parkingDto.completedAt();
		double hoursParked = ((double) Duration.between(entryTime, exitTime).toMinutes()) / 60.0;

		if (hoursParked <= (double) pricingRuleDto.freeParkingHours()) {
			return 0.0;
		}

		// Subtract free hours from the total time
		double chargedTime = hoursParked - pricingRuleDto.freeParkingHours();

		// Round UP the charged time to the nearest hour (as is common in parking)
		double chargeableHours = Math.ceil(chargedTime);

		double price = pricingRuleDto.hourlyRate() * chargeableHours;

		return price;
	}

}
