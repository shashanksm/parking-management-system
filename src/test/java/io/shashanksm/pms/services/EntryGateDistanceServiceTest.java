package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateDistanceCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDistanceDto;
import io.shashanksm.pms.dtos.EntryGateDistanceUpdateRequest;
import io.shashanksm.pms.entities.EntryGateDistance;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.entities.ParkingSlot;
import io.shashanksm.pms.repositories.EntryGateDistanceRepository;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntryGateDistanceServiceTest {

    @Mock
    private EntryGateDistanceRepository entryGateDistanceRepository;
    @Mock
    private ParkingLotEntryGateRepository entryGateRepository;
    @Mock
    private ParkingSlotRepository parkingSlotRepository;

    @InjectMocks
    private EntryGateDistanceService entryGateDistanceService;

    private ParkingLotEntryGate entryGate;
    private ParkingSlot parkingSlot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ParkingLot parkingLot = new ParkingLot(1L, "Test Lot", 5);
        entryGate = new ParkingLotEntryGate(1L, 1, parkingLot);
        parkingSlot = new ParkingSlot(1L, parkingLot, 101, 1, 1, true);
    }

    @Test
    void create_shouldReturnDto_whenValid() {
        // Given
        EntryGateDistanceCreateRequest request = new EntryGateDistanceCreateRequest(1L, 1L, 50);
        EntryGateDistance savedDistance = new EntryGateDistance(entryGate, parkingSlot, 50);

        when(entryGateRepository.findById(1L)).thenReturn(Optional.of(entryGate));
        when(parkingSlotRepository.findById(1L)).thenReturn(Optional.of(parkingSlot));
        when(entryGateDistanceRepository.findByParkingLotEntryGateAndParkingSlot(entryGate, parkingSlot)).thenReturn(Optional.empty());
        when(entryGateDistanceRepository.save(any(EntryGateDistance.class))).thenReturn(savedDistance);

        // When
        EntryGateDistanceDto result = entryGateDistanceService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(50, result.distance());
        
        verify(entryGateDistanceRepository, times(1)).save(any(EntryGateDistance.class));
    }
    
    @Test
    void create_shouldThrowException_whenDuplicateExists() {
        // Given
        EntryGateDistanceCreateRequest request = new EntryGateDistanceCreateRequest(1L, 1L, 50);
        when(entryGateRepository.findById(1L)).thenReturn(Optional.of(entryGate));
        when(parkingSlotRepository.findById(1L)).thenReturn(Optional.of(parkingSlot));
        when(entryGateDistanceRepository.findByParkingLotEntryGateAndParkingSlot(entryGate, parkingSlot)).thenReturn(Optional.of(new EntryGateDistance()));

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> entryGateDistanceService.create(request));
        verify(entryGateDistanceRepository, never()).save(any(EntryGateDistance.class));
    }
    
    @Test
    void findById_shouldReturnDto_whenFound() {
        // Given
        EntryGateDistance entity = new EntryGateDistance(entryGate, parkingSlot, 50);
        when(entryGateDistanceRepository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        EntryGateDistanceDto result = entryGateDistanceService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(50, result.distance());
        verify(entryGateDistanceRepository, times(1)).findById(1L);
    }

    @Test
    void delete_shouldSucceed() {
        // Given
        when(entryGateDistanceRepository.existsById(1L)).thenReturn(true);

        // When
        entryGateDistanceService.delete(1L);

        // Then
        verify(entryGateDistanceRepository, times(1)).existsById(1L);
        verify(entryGateDistanceRepository, times(1)).deleteById(1L);
    }
}