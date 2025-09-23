package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.ParkingSlotCreateRequest;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingSlotService(
        ParkingSlotRepository parkingSlotRepository,
        ParkingLotRepository parkingLotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    /**
     * Creates a new parking slot.
     * @param request The DTO containing the slot details.
     * @return A DTO of the newly created parking slot.
     * @throws ResourceNotFoundException if the parking lot is not found.
     * @throws DuplicateResourceException if a slot with the same number already exists on the floor.
     */
    @Transactional
    public ParkingSlotDto createParkingSlot(ParkingSlotCreateRequest request) {
        ParkingLot parkingLot = parkingLotRepository.findById(request.parkingLotId())
            .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + request.parkingLotId()));
        
        // Check for duplicate slot number on the same floor
        if (parkingSlotRepository.findByParkingLotAndFloorNumberAndSlotNumber(parkingLot, request.floorNumber(), request.slotNumber()).isPresent()) {
            throw new DuplicateResourceException("ParkingSlot with number " + request.slotNumber() + " already exists on floor " + request.floorNumber() + " for this parking lot.");
        }

        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setParkingLot(parkingLot);
        parkingSlot.setSlotNumber(request.slotNumber());
        parkingSlot.setFloorNumber(request.floorNumber());
        parkingSlot.setParkingType(request.parkingType());
        parkingSlot.setAvailable(request.available());

        ParkingSlot savedSlot = parkingSlotRepository.save(parkingSlot);
        return ParkingSlotDto.fromEntity(savedSlot);
    }

    /**
     * Retrieves a parking slot by its ID.
     * @param id The ID of the parking slot.
     * @return A DTO of the found parking slot.
     * @throws ResourceNotFoundException if the slot is not found.
     */
    @Transactional(readOnly = true)
    public ParkingSlotDto getParkingSlotById(Long id) {
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ParkingSlot not found with ID: " + id));
        return ParkingSlotDto.fromEntity(parkingSlot);
    }
    
    /**
     * Retrieves all parking slots.
     * @return A list of all parking slot DTOs.
     */
    @Transactional(readOnly = true)
    public List<ParkingSlotDto> getAllParkingSlots() {
        return parkingSlotRepository.findAll().stream()
            .map(ParkingSlotDto::fromEntity)
            .collect(Collectors.toList());
    }

    /**
     * Updates an existing parking slot.
     * @param id The ID of the slot to update.
     * @param request The DTO with the updated details.
     * @return A DTO of the updated parking slot.
     * @throws ResourceNotFoundException if the slot or parking lot is not found.
     */
    @Transactional
    public ParkingSlotDto updateParkingSlot(Long id, ParkingSlotCreateRequest request) {
        ParkingSlot existingSlot = parkingSlotRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ParkingSlot not found with ID: " + id));

        ParkingLot parkingLot = parkingLotRepository.findById(request.parkingLotId())
            .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + request.parkingLotId()));
            
        // Check for duplicate slot number if the number or floor changed
        if (!existingSlot.getSlotNumber().equals(request.slotNumber()) || !existingSlot.getFloorNumber().equals(request.floorNumber())) {
             if (parkingSlotRepository.findByParkingLotAndFloorNumberAndSlotNumber(parkingLot, request.floorNumber(), request.slotNumber()).isPresent()) {
                throw new DuplicateResourceException("ParkingSlot with number " + request.slotNumber() + " already exists on floor " + request.floorNumber() + " for this parking lot.");
            }
        }

        existingSlot.setParkingLot(parkingLot);
        existingSlot.setSlotNumber(request.slotNumber());
        existingSlot.setFloorNumber(request.floorNumber());
        existingSlot.setParkingType(request.parkingType());
        existingSlot.setAvailable(request.available());

        ParkingSlot updatedSlot = parkingSlotRepository.save(existingSlot);
        return ParkingSlotDto.fromEntity(updatedSlot);
    }

    /**
     * Deletes a parking slot by its ID.
     * @param id The ID of the parking slot to delete.
     * @throws ResourceNotFoundException if the slot is not found.
     */
    @Transactional
    public void deleteParkingSlot(Long id) {
        if (!parkingSlotRepository.existsById(id)) {
            throw new ResourceNotFoundException("ParkingSlot not found with ID: " + id);
        }
        parkingSlotRepository.deleteById(id);
    }
}