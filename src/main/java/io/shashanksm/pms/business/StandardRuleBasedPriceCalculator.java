package io.shashanksm.pms.business;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.shashanksm.pms.business.exceptions.NoPricingRuleFoundException;
import io.shashanksm.pms.business.exceptions.ParkingNotCompletedException;
import io.shashanksm.pms.dtos.ParkingDto;
import io.shashanksm.pms.dtos.PricingRuleDto;
import io.shashanksm.pms.dtos.VehicleDto;
import io.shashanksm.pms.entities.PricingRule;
import io.shashanksm.pms.repositories.PricingRuleRepository;
import io.shashanksm.pms.repositories.VehicleRepository;

@Service
public class StandardRuleBasedPriceCalculator implements PriceCalculator {

	private static final Logger log = LoggerFactory.getLogger(StandardRuleBasedPriceCalculator.class);
	
	private PricingRuleRepository pricingRuleRepository;
	
	private VehicleRepository vehicleRepository;

	public StandardRuleBasedPriceCalculator(PricingRuleRepository pricingRuleRepository, VehicleRepository vehicleRepository) {
		super();
		this.pricingRuleRepository = pricingRuleRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public Double calculatePrice(ParkingDto parkingDto) {
		
		//check if parking is complete
		if(parkingDto.completedAt() == null)
			throw new ParkingNotCompletedException();
		
		//get the first active pricing-rule
		
		log.info("vehicle id = "+parkingDto.vehicleId());
		int vehicleType = vehicleRepository.findById(parkingDto.vehicleId()).get().getType();
		
		Optional<PricingRule> pricingRuleOptional = pricingRuleRepository.findByVehicleTypeAndActiveTrue(vehicleType);

		if(pricingRuleOptional.isEmpty()) {
			throw new NoPricingRuleFoundException();
		}
		
		PricingRuleDto pricingRuleDto = PricingRuleDto.fromEntity(pricingRuleOptional.get());
		
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
