package io.shashanksm.pms.business;

import io.shashanksm.pms.business.exceptions.ParkingNotCompletedException;
import io.shashanksm.pms.dtos.ParkingDto;

public class FlatFeePriceCalculator implements PriceCalculator {

	private double flatFee = 0.0;

	public double getFlatFee() {
		return flatFee;
	}

	public void setFlatFee(double flatFee) {
		this.flatFee = flatFee;
	}

	public FlatFeePriceCalculator(double flatFee) {
		super();
		this.flatFee = flatFee;
	}

	@Override
	public Double calculatePrice(ParkingDto parkingDto) {
		if(parkingDto.completedAt() == null)
			throw new ParkingNotCompletedException();
		
		return flatFee;
	}

}
