package io.shashanksm.pms.services;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.shashanksm.pms.business.PriceCalculator;
import io.shashanksm.pms.business.exceptions.ParkingNotCompletedException;
import io.shashanksm.pms.business.exceptions.ParkingNotFoundException;
import io.shashanksm.pms.dtos.ParkingDto;
import io.shashanksm.pms.dtos.ParkingReceipt;
import io.shashanksm.pms.entities.Parking;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.entities.Vehicle;
import io.shashanksm.pms.repositories.ParkingRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import io.shashanksm.pms.repositories.VehicleRepository;

@Service
public class ParkingPaymentService {

	private static final Logger log = LoggerFactory.getLogger(ParkingPaymentService.class);

	private ParkingRepository parkingRepository;

	private PriceCalculator priceCalculator;

	private ParkingSlotRepository parkingSlotRepository;

	private VehicleRepository vehicleRepository;

	public ParkingPaymentService(ParkingRepository parkingRepository, ParkingSlotRepository parkingSlotRepository,
			PriceCalculator priceCalculator, VehicleRepository vehicleRepository) {
		this.parkingRepository = parkingRepository;
		this.parkingSlotRepository = parkingSlotRepository;
		this.priceCalculator = priceCalculator;
		this.vehicleRepository = vehicleRepository;
	}

	@Transactional
	public ParkingReceipt createParkingReceipt(Long parkingId, Instant exitTime) {

		Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

		if (parkingOpt.isEmpty()) {
			throw new RuntimeException("parking with id=" + parkingId + " was not found");
		}

		Parking parking = parkingOpt.get();

		parking.setCompletedAt(exitTime);

		ParkingDto parkingDto = ParkingDto.fromEntity(parking);

		Double amount = priceCalculator.calculatePrice(parkingDto);

		log.info(parking.toString());

		String owner = parking.getVehicle().getOwner();

		String vehicleNumber = parking.getVehicle().getVehicleNumber();

		String parkingLot = parking.getParkingSlot().getParkingLot().getName();

		int slotNumber = parking.getParkingSlot().getSlotNumber();

		Instant entryTime = parking.getCreatedAt();

		return ParkingReceipt.builder().id(parkingId).entryTime(parking.getCreatedAt()).owner(owner)
				.vehicleNumber(vehicleNumber).parkingLot(parkingLot).slotNumber(slotNumber).entryTime(entryTime)
				.exitTime(exitTime).paymentAmount(amount).build();
	}

	@Transactional
	public void acceptPayment(Long parkingId, Instant exitTime) {
		log.info("retrieving parking with id " + parkingId);

		Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

		if (parkingOpt.isEmpty()) {
			throw new RuntimeException("parking with id=" + parkingId + " was not found");
		}

		Parking parking = parkingOpt.get();

		parking.setCompletedAt(exitTime);

		ParkingDto parkingDto = ParkingDto.fromEntity(parking);

		log.info("calculating bill amount");

		Double paymentAmount = priceCalculator.calculatePrice(parkingDto);

		parking.setActive(false);

		parking.setPaymentAmount(paymentAmount);

		parking.setPaymentStatus(true);

		parkingRepository.save(parking);
	}

	@Transactional
	public void exit(long parkingId) {
		log.info("checking if parking is completed");

		Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

		if (parkingOpt.isEmpty()) {
			throw new RuntimeException("parking with id=" + parkingId + " was not found");
		}

		Parking parking = parkingOpt.get();
		
		if(parking.getActive() || !parking.getPaymentStatus()) {
			throw new ParkingNotCompletedException();
		}
		
		log.info("getting the vehicle & parking-slot");
		
		Vehicle vehicle = parking.getVehicle();
		
		ParkingSlot parkingSlot = parking.getParkingSlot();
		
		log.info("deleting parking entry");
		
		parkingRepository.delete(parking);
		
		log.info("freeing parking slot");
		
		parkingSlot.setAvailable(true);
		
		parkingSlotRepository.save(parkingSlot);
		
		log.info("deleting vehicle entry");
		
		vehicleRepository.delete(vehicle);
		
		log.info("done");
		
	}
}
