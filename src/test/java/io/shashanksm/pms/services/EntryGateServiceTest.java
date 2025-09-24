package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateGateNumberException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDto;
import io.shashanksm.pms.entities.ParkingLot;
import io.shashanksm.pms.entities.ParkingLotEntryGate;
import io.shashanksm.pms.repositories.ParkingLotEntryGateRepository;
import io.shashanksm.pms.repositories.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntryGateServiceTest {

    @Mock
    private ParkingLotEntryGateRepository entryGateRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private EntryGateService entryGateService;

    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parkingLot = new ParkingLot(1L, "Test Lot", 5);
    }

    @Test
    void createEntryGate_shouldReturnDto_whenValid() {
        // Given
        EntryGateCreateRequest request = new EntryGateCreateRequest(1L, 1);
        
        // Corrected constructor call here
        ParkingLotEntryGate savedGate = new ParkingLotEntryGate(2L, 1, parkingLot); 

        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(entryGateRepository.findByParkingLotAndGateNumber(parkingLot, 1)).thenReturn(Optional.empty());
        when(entryGateRepository.save(any(ParkingLotEntryGate.class))).thenReturn(savedGate);

        // When
        EntryGateDto result = entryGateService.createEntryGate(request);

        // Then
        assertNotNull(result);
        
        assertEquals(1, result.gateNumber());
        verify(entryGateRepository, times(1)).save(any(ParkingLotEntryGate.class));
    }

    @Test
    void createEntryGate_shouldThrowException_whenGateNumberExists() {
        // Given
        EntryGateCreateRequest request = new EntryGateCreateRequest(1L, 1);
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(entryGateRepository.findByParkingLotAndGateNumber(parkingLot, 1)).thenReturn(Optional.of(new ParkingLotEntryGate()));

        // When & Then
        assertThrows(DuplicateGateNumberException.class, () -> entryGateService.createEntryGate(request));
        verify(entryGateRepository, never()).save(any(ParkingLotEntryGate.class));
    }

    @Test
    void getEntryGateById_shouldReturnDto_whenFound() {
        // Given
        // Corrected constructor call here
        ParkingLotEntryGate entity = new ParkingLotEntryGate(1L, 1, parkingLot); 
        when(entryGateRepository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        EntryGateDto result = entryGateService.getEntryGateById(1L);

        // Then
        assertNotNull(result);
        
        verify(entryGateRepository, times(1)).findById(1L);
    }

    @Test
    void getEntryGateById_shouldThrowException_whenNotFound() {
        // Given
        when(entryGateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> entryGateService.getEntryGateById(1L));
        verify(entryGateRepository, times(1)).findById(1L);
    }

    @Test
    void deleteEntryGate_shouldSucceed() {
        // Given
        when(entryGateRepository.existsById(1L)).thenReturn(true);
        
        // When
        entryGateService.deleteEntryGate(1L);

        // Then
        verify(entryGateRepository, times(1)).existsById(1L);
        verify(entryGateRepository, times(1)).deleteById(1L);
    }
}