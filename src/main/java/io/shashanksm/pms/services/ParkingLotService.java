package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.ParkingLotDto;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional
    public ParkingLotDto createParkingLot(ParkingLotDto dto) {
        ParkingLot parkingLot = new ParkingLot(null, dto.name(), dto.floors());
        
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        return ParkingLotDto.fromEntity(savedParkingLot);
    }

    @Transactional(readOnly = true)
    public ParkingLotDto getParkingLotById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + id));
        return ParkingLotDto.fromEntity(parkingLot);
    }

    @Transactional(readOnly = true)
    public List<ParkingLotDto> getAllParkingLots() {
        return parkingLotRepository.findAll().stream()
                .map(ParkingLotDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParkingLotDto updateParkingLot(Long id, ParkingLotDto dto) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found with ID: " + id));

        parkingLot.setName(dto.name());
        parkingLot.setFloors(dto.floors());

        ParkingLot updatedParkingLot = parkingLotRepository.save(parkingLot);
        return ParkingLotDto.fromEntity(updatedParkingLot);
    }

    @Transactional
    public void deleteParkingLot(Long id) {
        if (!parkingLotRepository.existsById(id)) {
            throw new ResourceNotFoundException("ParkingLot not found with ID: " + id);
        }
        parkingLotRepository.deleteById(id);
    }
}