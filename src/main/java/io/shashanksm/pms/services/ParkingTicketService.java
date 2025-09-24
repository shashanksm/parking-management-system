package io.shashanksm.pms.services;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.shashanksm.pms.business.ParkingAllotmentStrategy;
import io.shashanksm.pms.business.exceptions.InvalidGateDetailsException;
import io.shashanksm.pms.controllers.requests.ParkingEntryRequest;
import io.shashanksm.pms.dtos.ParkingLotEntryGateDto;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.dtos.ParkingTicket;
import io.shashanksm.pms.dtos.VehicleDto;
import io.shashanksm.pms.entities.Parking;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.entities.Vehicle;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import io.shashanksm.pms.repositories.ParkingRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import io.shashanksm.pms.repositories.VehicleRepository;
import jakarta.transaction.Transactional;

@Service
public class ParkingTicketService {

	private static final Logger log = LoggerFactory.getLogger(ParkingTicketService.class);

	private VehicleRepository vehicleRepository;
	private ParkingLotRepository parkingLotRepository;
	private ParkingSlotRepository parkingSlotRepository;
	private ParkingLotEntryGateRepository parkingLotEntryGateRepository;
	private ParkingAllotmentStrategy parkingAllotmentStrategy;
	private ParkingRepository parkingRepository;

	public ParkingTicketService(VehicleRepository vehicleRepository,
			ParkingLotRepository parkingLotRepository,
			ParkingSlotRepository parkingSlotRepository, ParkingLotEntryGateRepository parkingLotEntryGateRepository,
			ParkingAllotmentStrategy parkingAllotmentStrategy, ParkingRepository parkingRepository) {
		this.parkingLotEntryGateRepository = parkingLotEntryGateRepository;
		this.parkingLotRepository = parkingLotRepository;
		this.vehicleRepository = vehicleRepository;
		this.parkingSlotRepository = parkingSlotRepository;
		this.parkingAllotmentStrategy = parkingAllotmentStrategy;
		this.parkingRepository = parkingRepository;
	}

	@Transactional
	public Optional<ParkingTicket> issueParkingTicket(ParkingEntryRequest parkingEntryRequest) {
		Optional<ParkingTicket> ret = Optional.empty();

		log.info("creating vehicle entry");

		String vehicleNumber = parkingEntryRequest.vehicleNumber();
		String owner = parkingEntryRequest.owner();
		int type = parkingEntryRequest.vehicleType();
		Vehicle vehicle = new Vehicle(null, vehicleNumber, owner, type);
		vehicle = vehicleRepository.save(vehicle);

		VehicleDto vehicleDto = VehicleDto.fromEntity(vehicle);

		log.info("getting entry details");

		String location = parkingEntryRequest.location();
		int gateNumber = parkingEntryRequest.gateNumber();

		log.info("getting parking-lot details");
		
		Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findByName(location);
		
		if(parkingLotOptional.isEmpty()) {
			throw new RuntimeException("parking-lot with name not found");
		}
		
		ParkingLot parkingLot = parkingLotOptional.get();
		
		log.info("getting entry-gate details");
		
		Optional<ParkingLotEntryGate> gateOptional = parkingLotEntryGateRepository.findByParkingLotAndGateNumber(parkingLot, gateNumber);

		if (gateOptional.isEmpty()) {
			throw new InvalidGateDetailsException("gate with parking-lot and gate-number was not found");
		}

		ParkingLotEntryGate parkingLotEntryGate = gateOptional.get();

		ParkingLotEntryGateDto parkingLotEntryGateDto = ParkingLotEntryGateDto.fromEntity(parkingLotEntryGate);

		log.info("getting available parking-slot");

		ParkingSlotDto parkingSlotDto = parkingAllotmentStrategy.assignParkingSlot(vehicleDto, parkingLotEntryGateDto);

		ParkingSlot parkingSlot = parkingSlotRepository.findById(parkingSlotDto.id()).get();

		log.info("making parking-slot unavailable");

		parkingSlot.setAvailable(false);

		parkingSlot = parkingSlotRepository.save(parkingSlot);

		log.info("create parking entry");

		Parking parking = new Parking(parkingSlot, vehicle, parkingEntryRequest.entryTime());

		parking = parkingRepository.save(parking);

		log.info("returning parking ticket");

		ParkingTicket parkingTicket = ParkingTicket.builder().entryGate(gateNumber)
				.entryTime(parkingEntryRequest.entryTime()).id(parking.getId()).owner(owner).parkingLot(location)
				.slotNumber(parkingSlot.getSlotNumber()).vehicleNumber(vehicleNumber).build();
		ret = Optional.of(parkingTicket);

		return ret;
	}
	
	public ParkingTicket generateParkingTicketByParkingId(long parkingId) {
		
		Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);
		
		if(parkingOpt.isEmpty()) {
			throw new RuntimeException("parking with id does not exist");
		}
		
		Parking parking = parkingOpt.get();
		
		Vehicle vehicle = parking.getVehicle();
		
		ParkingSlot parkingSlot = parking.getParkingSlot();
		
		return ParkingTicket.builder()
				.id(parkingId)
				.owner(vehicle.getOwner())
				.vehicleNumber(vehicle.getVehicleNumber())
				.parkingLot(parkingSlot.getParkingLot().getName())
				.slotNumber(parkingSlot.getSlotNumber())
				.entryTime(parking.getCreatedAt())
				.build();
	}

}
