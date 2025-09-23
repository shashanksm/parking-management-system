package io.shashanksm.pms.api;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.ParkingSlotCreateRequest;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.services.ParkingSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/parking-lots/{parkingLotId}/parking-slots")
public class ParkingSlotController {

	private final ParkingSlotService parkingSlotService;

	public ParkingSlotController(ParkingSlotService parkingSlotService) {
		this.parkingSlotService = parkingSlotService;
	}

	/**
	 * Creates a new parking slot for a specific parking lot. POST
	 * /api/admin/parking-lots/{parkingLotId}/parking-slots
	 */
	@PostMapping
	public ResponseEntity<ParkingSlotDto> createParkingSlot(@PathVariable Long parkingLotId,
			@RequestBody ParkingSlotCreateRequest request) {
		try {
			// Ensure the request body uses the path variable's parkingLotId
			ParkingSlotCreateRequest validatedRequest = new ParkingSlotCreateRequest(parkingLotId, request.slotNumber(),
					request.floorNumber(), request.parkingType(), request.available());

			ParkingSlotDto createdSlot = parkingSlotService.createParkingSlot(validatedRequest);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(createdSlot.id()).toUri();

			return ResponseEntity.created(location).body(createdSlot);
		} catch (DuplicateResourceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}
	}

	/**
	 * Retrieves all parking slots for a specific parking lot. GET
	 * /api/admin/parking-lots/{parkingLotId}/parking-slots
	 */
	@GetMapping
	public ResponseEntity<List<ParkingSlotDto>> getAllParkingSlots(@PathVariable Long parkingLotId) {
		List<ParkingSlotDto> allSlots = parkingSlotService.getAllParkingSlots();
		List<ParkingSlotDto> filteredSlots = allSlots.stream().filter(slot -> slot.parkingLotId().equals(parkingLotId))
				.collect(Collectors.toList());
		return ResponseEntity.ok(filteredSlots);
	}

	/**
	 * Retrieves a specific parking slot by ID, scoped to a parking lot. GET
	 * /api/admin/parking-lots/{parkingLotId}/parking-slots/{slotId}
	 */
	@GetMapping("/{slotId}")
	public ResponseEntity<ParkingSlotDto> getParkingSlotById(@PathVariable Long parkingLotId,
			@PathVariable Long slotId) {
		try {
			ParkingSlotDto slot = parkingSlotService.getParkingSlotById(slotId);
			if (!slot.parkingLotId().equals(parkingLotId)) {
				throw new ResourceNotFoundException(
						"ParkingSlot with ID " + slotId + " not found under ParkingLot with ID " + parkingLotId);
			}
			return ResponseEntity.ok(slot);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	/**
	 * Updates an existing parking slot. PUT
	 * /api/admin/parking-lots/{parkingLotId}/parking-slots/{slotId}
	 */
	@PutMapping("/{slotId}")
	public ResponseEntity<ParkingSlotDto> updateParkingSlot(@PathVariable Long parkingLotId, @PathVariable Long slotId,
			@RequestBody ParkingSlotCreateRequest request) {
		try {
			ParkingSlotCreateRequest validatedRequest = new ParkingSlotCreateRequest(parkingLotId, request.slotNumber(),
					request.floorNumber(), request.parkingType(), request.available());
			ParkingSlotDto updatedSlot = parkingSlotService.updateParkingSlot(slotId, validatedRequest);
			return ResponseEntity.ok(updatedSlot);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (DuplicateResourceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}
	}

	/**
	 * Deletes a specific parking slot. DELETE
	 * /api/admin/parking-lots/{parkingLotId}/parking-slots/{slotId}
	 */
	@DeleteMapping("/{slotId}")
	public ResponseEntity<Void> deleteParkingSlot(@PathVariable Long parkingLotId, @PathVariable Long slotId) {
		try {
			ParkingSlotDto slot = parkingSlotService.getParkingSlotById(slotId);
			if (!slot.parkingLotId().equals(parkingLotId)) {
				throw new ResourceNotFoundException(
						"ParkingSlot with ID " + slotId + " not found under ParkingLot with ID " + parkingLotId);
			}
			parkingSlotService.deleteParkingSlot(slotId);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}