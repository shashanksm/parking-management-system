package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.ParkingSlotCreateRequest;
import io.shashanksm.pms.dtos.ParkingSlotDto;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingSlotServiceTest {

    @Mock
    private ParkingSlotRepository parkingSlotRepository;
    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingSlotService parkingSlotService;

    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parkingLot = new ParkingLot(1L, "Test Lot", 5);
    }

    @Test
    void createParkingSlot_shouldReturnDto_whenValid() {
        // Given
        ParkingSlotCreateRequest request = new ParkingSlotCreateRequest(1L, 101, 1, 1, true);
        ParkingSlot savedSlot = new ParkingSlot(1L, parkingLot, 101, 1, 1, true);

        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(parkingSlotRepository.findByParkingLotAndFloorNumberAndSlotNumber(parkingLot, 1, 101)).thenReturn(Optional.empty());
        when(parkingSlotRepository.save(any(ParkingSlot.class))).thenReturn(savedSlot);

        // When
        ParkingSlotDto result = parkingSlotService.createParkingSlot(request);

        // Then
        assertNotNull(result);
        assertEquals(101, result.slotNumber());
        assertEquals(1, result.floorNumber());
        verify(parkingSlotRepository, times(1)).save(any(ParkingSlot.class));
    }
    
    @Test
    void createParkingSlot_shouldThrowException_whenDuplicateExists() {
        // Given
        ParkingSlotCreateRequest request = new ParkingSlotCreateRequest(1L, 101, 1, 1, true);
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(parkingSlotRepository.findByParkingLotAndFloorNumberAndSlotNumber(parkingLot, 1, 101)).thenReturn(Optional.of(new ParkingSlot()));

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> parkingSlotService.createParkingSlot(request));
        verify(parkingSlotRepository, never()).save(any(ParkingSlot.class));
    }
    
    @Test
    void getParkingSlotById_shouldReturnDto_whenFound() {
        // Given
        ParkingSlot entity = new ParkingSlot(1L, parkingLot, 101, 1, 1, true);
        when(parkingSlotRepository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        ParkingSlotDto result = parkingSlotService.getParkingSlotById(1L);

        // Then
        assertNotNull(result);
        assertEquals(101, result.slotNumber());
        assertEquals(1, result.floorNumber());
        verify(parkingSlotRepository, times(1)).findById(1L);
    }
    
    @Test
    void updateParkingSlot_shouldReturnUpdatedDto() {
        // Given
        ParkingSlotCreateRequest request = new ParkingSlotCreateRequest(1L, 102, 1, 2, false);
        ParkingSlot existingSlot = new ParkingSlot(1L, parkingLot, 101, 1, 1, true);
        ParkingSlot updatedSlot = new ParkingSlot(1L, parkingLot, 102, 1, 2, false);

        when(parkingSlotRepository.findById(1L)).thenReturn(Optional.of(existingSlot));
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(parkingSlotRepository.findByParkingLotAndFloorNumberAndSlotNumber(parkingLot, 1, 102)).thenReturn(Optional.empty());
        when(parkingSlotRepository.save(any(ParkingSlot.class))).thenReturn(updatedSlot);

        // When
        ParkingSlotDto result = parkingSlotService.updateParkingSlot(1L, request);

        // Then
        assertNotNull(result);
        assertEquals(102, result.slotNumber());
        assertEquals(2, result.parkingType());
        verify(parkingSlotRepository, times(1)).findById(1L);
        verify(parkingSlotRepository, times(1)).save(any(ParkingSlot.class));
    }
    
    @Test
    void deleteParkingSlot_shouldSucceed() {
        // Given
        when(parkingSlotRepository.existsById(1L)).thenReturn(true);
        
        // When
        parkingSlotService.deleteParkingSlot(1L);
        
        // Then
        verify(parkingSlotRepository, times(1)).existsById(1L);
        verify(parkingSlotRepository, times(1)).deleteById(1L);
    }
}