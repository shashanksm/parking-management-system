package io.shashanksm.pms.business;

import io.shashanksm.pms.dtos.ParkingDto;

@FunctionalInterface
public interface PriceCalculator {
	public Double calculatePrice(ParkingDto parkingDto);
}
