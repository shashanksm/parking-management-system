package io.shashanksm.pms.services;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.shashanksm.pms.business.PriceCalculator;
import io.shashanksm.pms.dtos.ParkingDto;
import io.shashanksm.pms.dtos.ParkingReceipt;
import io.shashanksm.pms.entities.Parking;
import io.shashanksm.pms.repositories.ParkingRepository;
import jakarta.transaction.Transactional;

@Service
public class ParkingPaymentService {
	
	private static final Logger log = LoggerFactory.getLogger(ParkingPaymentService.class);
	
	private ParkingRepository parkingRepository;
		
	private PriceCalculator priceCalculator;
	
	public ParkingPaymentService(ParkingRepository parkingRepository, PriceCalculator priceCalculator) {
		this.parkingRepository = parkingRepository;
		this.priceCalculator = priceCalculator;
	}
	
	
	@Transactional
	public ParkingReceipt createParkingReceipt(Long parkingId, Instant exitTime) {
		
		Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);
		
		if(parkingOpt.isEmpty()) {
			throw new RuntimeException("parking with id="+parkingId+" was not found");
		}
		
		Parking parking = parkingOpt.get();
		
		parking.setCompletedAt(exitTime);
		
		ParkingDto parkingDto = ParkingDto.fromEntity(parking);
		
		Double amount = priceCalculator.calculatePrice(parkingDto);
		
		String owner = parking.getVehicle().getOwner();
		
		String vehicleNumber = parking.getVehicle().getVehicleNumber();
		
		String parkingLot = parking.getParkingSlot().getParkingLot().getName();
		
		int slotNumber = parking.getParkingSlot().getSlotNumber();
		
		Instant entryTime = parking.getCreatedAt();
		
		
		return ParkingReceipt.builder()
				.id(parkingId)
				.entryTime(parking.getCreatedAt())
				.owner(owner)
				.vehicleNumber(vehicleNumber)
				.parkingLot(parkingLot)
				.slotNumber(slotNumber)
				.entryTime(entryTime)
				.exitTime(exitTime)
				.paymentAmount(amount)
				.build();
	}
	
}
