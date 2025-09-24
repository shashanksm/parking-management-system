package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateDistanceCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDistanceDto;
import io.shashanksm.pms.dtos.EntryGateDistanceUpdateRequest;
import io.shashanksm.pms.entities.EntryGateDistance;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.repositories.EntryGateDistanceRepository;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryGateDistanceService {

    private final EntryGateDistanceRepository entryGateDistanceRepository;
    private final ParkingLotEntryGateRepository entryGateRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    public EntryGateDistanceService(
            EntryGateDistanceRepository entryGateDistanceRepository,
            ParkingLotEntryGateRepository entryGateRepository,
            ParkingSlotRepository parkingSlotRepository) {
        this.entryGateDistanceRepository = entryGateDistanceRepository;
        this.entryGateRepository = entryGateRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    /**
     * Creates a new EntryGateDistance record.
     * @param request The DTO containing the data for the new record.
     * @return The newly created DTO.
     * @throws ResourceNotFoundException if the gate or slot IDs are invalid.
     * @throws DuplicateResourceException if a distance record for the same gate and slot already exists.
     */
    @Transactional
    public EntryGateDistanceDto create(EntryGateDistanceCreateRequest request) {
        // 1. Fetch the related entities
        ParkingLotEntryGate gate = entryGateRepository.findById(request.parkingLotEntryGateId())
                .orElseThrow(() -> new ResourceNotFoundException("EntryGate not found with ID: " + request.parkingLotEntryGateId()));
        
        ParkingSlot slot = parkingSlotRepository.findById(request.parkingSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSlot not found with ID: " + request.parkingSlotId()));
        
        // 2. Check for duplicate record
        if (entryGateDistanceRepository.findByParkingLotEntryGateAndParkingSlot(gate, slot).isPresent()) {
            throw new DuplicateResourceException("Distance record already exists for Gate ID: " + gate.getId() + " and Slot ID: " + slot.getId());
        }
        
        // 3. Create and save the new entity
        EntryGateDistance newDistance = new EntryGateDistance(gate, slot, request.distance());
        EntryGateDistance savedDistance = entryGateDistanceRepository.save(newDistance);
        
        // 4. Convert to DTO and return
        return EntryGateDistanceDto.fromEntity(savedDistance);
    }

    /**
     * Retrieves an EntryGateDistanceDto by its ID.
     * @param id The ID of the EntryGateDistance entity.
     * @return An Optional containing the DTO, or an empty Optional if not found.
     */
    @Transactional(readOnly = true)
    public EntryGateDistanceDto findById(Long id) {
        return entryGateDistanceRepository.findById(id)
                .map(EntryGateDistanceDto::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("EntryGateDistance not found with ID: " + id));
    }

    /**
     * Retrieves all EntryGateDistanceDto records.
     * @return A list of all distance DTOs.
     */
    @Transactional(readOnly = true)
    public List<EntryGateDistanceDto> findAll() {
        return entryGateDistanceRepository.findAll().stream()
                .map(EntryGateDistanceDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing EntryGateDistance record.
     * @param request The DTO containing the updated data.
     * @return The updated DTO.
     * @throws ResourceNotFoundException if the record ID or related IDs are invalid.
     */
    @Transactional
    public EntryGateDistanceDto update(EntryGateDistanceUpdateRequest request) {
        // 1. Fetch the existing entity
        EntryGateDistance existingDistance = entryGateDistanceRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("EntryGateDistance not found with ID: " + request.id()));

        // 2. Fetch and update the related entities if their IDs have changed
        if (!existingDistance.getParkingLotEntryGate().getId().equals(request.parkingLotEntryGateId())) {
            ParkingLotEntryGate newGate = entryGateRepository.findById(request.parkingLotEntryGateId())
                    .orElseThrow(() -> new ResourceNotFoundException("EntryGate not found with ID: " + request.parkingLotEntryGateId()));
            existingDistance.setParkingLotEntryGate(newGate);
        }

        if (!existingDistance.getParkingSlot().getId().equals(request.parkingSlotId())) {
            ParkingSlot newSlot = parkingSlotRepository.findById(request.parkingSlotId())
                    .orElseThrow(() -> new ResourceNotFoundException("ParkingSlot not found with ID: " + request.parkingSlotId()));
            existingDistance.setParkingSlot(newSlot);
        }

        // 3. Check for a duplicate record with the new gate/slot combination
        entryGateDistanceRepository.findByParkingLotEntryGateAndParkingSlot(
            existingDistance.getParkingLotEntryGate(), existingDistance.getParkingSlot()
        ).ifPresent(found -> {
            if (!found.getId().equals(existingDistance.getId())) {
                 throw new DuplicateResourceException("Distance record already exists for Gate ID: " + found.getParkingLotEntryGate().getId() + " and Slot ID: " + found.getParkingSlot().getId());
            }
        });
        
        // 4. Update the distance
        existingDistance.setDistance(request.distance());

        // 5. Save the updated entity and convert to DTO
        EntryGateDistance updatedDistance = entryGateDistanceRepository.save(existingDistance);
        return EntryGateDistanceDto.fromEntity(updatedDistance);
    }
    
    /**
     * Deletes an EntryGateDistance record by its ID.
     * @param id The ID of the record to delete.
     * @throws ResourceNotFoundException if the record is not found.
     */
    @Transactional
    public void delete(Long id) {
        if (!entryGateDistanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("EntryGateDistance not found with ID: " + id);
        }
        entryGateDistanceRepository.deleteById(id);
    }
}