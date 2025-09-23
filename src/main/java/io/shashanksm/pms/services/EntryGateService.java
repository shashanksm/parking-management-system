package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateGateNumberException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDto;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryGateService {

	private final ParkingLotEntryGateRepository entryGateRepository;
    private final ParkingLotRepository parkingLotRepository;

    public EntryGateService(
        ParkingLotEntryGateRepository entryGateRepository,
        ParkingLotRepository parkingLotRepository) {
        this.entryGateRepository = entryGateRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    /**
     * Creates a new parking lot entry gate.
     * @param request The DTO containing the gate details.
     * @return A DTO of the newly created entry gate.
     * @throws DuplicateGateNumberException if a gate with the same number already exists
     * for the given parking lot.
     */
    @Transactional
    public EntryGateDto createEntryGate(EntryGateCreateRequest request) {
        // 1. Fetch the related ParkingLot entity
        ParkingLot parkingLot = parkingLotRepository.findById(request.parkingLotId())
            .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + request.parkingLotId()));

        // 2. Check for duplicate gate number
        if (entryGateRepository.findByParkingLotAndGateNumber(parkingLot, request.gateNumber()).isPresent()) {
            throw new DuplicateGateNumberException("Gate number " + request.gateNumber() + " already exists for parking lot " + parkingLot.getName());
        }

        // 3. Create, save, and return the new entity
        ParkingLotEntryGate entryGate = new ParkingLotEntryGate();
        entryGate.setParkingLot(parkingLot);
        entryGate.setGateNumber(request.gateNumber());

        ParkingLotEntryGate savedGate = entryGateRepository.save(entryGate);
        return EntryGateDto.fromEntity(savedGate);
    }

    @Transactional(readOnly = true)
    public EntryGateDto getEntryGateById(Long id) {
        ParkingLotEntryGate entryGate = entryGateRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("EntryGate not found with ID: " + id));
        return EntryGateDto.fromEntity(entryGate);
    }

    @Transactional(readOnly = true)
    public List<EntryGateDto> getAllEntryGates() {
        return entryGateRepository.findAll().stream()
            .map(EntryGateDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public EntryGateDto updateEntryGate(Long id, EntryGateCreateRequest request) {
        ParkingLotEntryGate existingGate = entryGateRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("EntryGate not found with ID: " + id));

        ParkingLot parkingLot = parkingLotRepository.findById(request.parkingLotId())
            .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + request.parkingLotId()));

        existingGate.setParkingLot(parkingLot);
        existingGate.setGateNumber(request.gateNumber());

        ParkingLotEntryGate updatedGate = entryGateRepository.save(existingGate);
        return EntryGateDto.fromEntity(updatedGate);
    }

    @Transactional
    public void deleteEntryGate(Long id) {
        if (!entryGateRepository.existsById(id)) {
            throw new ResourceNotFoundException("EntryGate not found with ID: " + id);
        }
        entryGateRepository.deleteById(id);
    }
}