package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.ParkingLotDto;
import io.shashanksm.pms.entities.ParkingLot;
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

class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotService parkingLotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParkingLot_shouldReturnDto() {
        // Given
        ParkingLotDto dto = new ParkingLotDto(null, "Test Lot", 5);
        ParkingLot entity = new ParkingLot(1L, "Test Lot", 5);
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(entity);

        // When
        ParkingLotDto result = parkingLotService.createParkingLot(dto);

        // Then
        assertNotNull(result);
        assertEquals("Test Lot", result.name());
        verify(parkingLotRepository, times(1)).save(any(ParkingLot.class));
    }

    @Test
    void getParkingLotById_shouldReturnDto_whenFound() {
        // Given
        ParkingLot entity = new ParkingLot(1L, "Test Lot", 5);
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        ParkingLotDto result = parkingLotService.getParkingLotById(1L);

        // Then
        assertNotNull(result);
        verify(parkingLotRepository, times(1)).findById(1L);
    }

    @Test
    void getParkingLotById_shouldThrowException_whenNotFound() {
        // Given
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> parkingLotService.getParkingLotById(1L));
        verify(parkingLotRepository, times(1)).findById(1L);
    }

    @Test
    void getAllParkingLots_shouldReturnListOfDtos() {
        // Given
        List<ParkingLot> parkingLots = List.of(
            new ParkingLot(1L, "Lot A", 3),
            new ParkingLot(2L, "Lot B", 2)
        );
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);

        // When
        List<ParkingLotDto> result = parkingLotService.getAllParkingLots();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Lot A", result.get(0).name());
        verify(parkingLotRepository, times(1)).findAll();
    }
    
    @Test
    void updateParkingLot_shouldReturnUpdatedDto() {
        // Given
        ParkingLotDto updateDto = new ParkingLotDto(1L, "Updated Lot", 7);
        ParkingLot existingEntity = new ParkingLot(1L, "Old Lot", 5);
        ParkingLot updatedEntity = new ParkingLot(1L, "Updated Lot", 7);
        
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(updatedEntity);

        // When
        ParkingLotDto result = parkingLotService.updateParkingLot(1L, updateDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Lot", result.name());
        assertEquals(7, result.floors());
        verify(parkingLotRepository, times(1)).findById(1L);
        verify(parkingLotRepository, times(1)).save(any(ParkingLot.class));
    }

    @Test
    void deleteParkingLot_shouldSucceed_whenFound() {
        // Given
        when(parkingLotRepository.existsById(1L)).thenReturn(true);
        
        // When
        parkingLotService.deleteParkingLot(1L);

        // Then
        verify(parkingLotRepository, times(1)).existsById(1L);
        verify(parkingLotRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteParkingLot_shouldThrowException_whenNotFound() {
        // Given
        when(parkingLotRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> parkingLotService.deleteParkingLot(1L));
        verify(parkingLotRepository, times(1)).existsById(1L);
        verify(parkingLotRepository, never()).deleteById(anyLong());
    }
}