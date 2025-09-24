package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.PricingRuleCreateRequest;
import io.shashanksm.pms.dtos.PricingRuleDto;
import io.shashanksm.pms.entities.PricingRule;
import io.shashanksm.pms.repositories.PricingRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PricingRuleServiceTest {

    @Mock
    private PricingRuleRepository pricingRuleRepository;

    @InjectMocks
    private PricingRuleService pricingRuleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPricingRule_shouldReturnDto_whenValid() {
        // Given
        PricingRuleCreateRequest request = new PricingRuleCreateRequest("Car Rate", "Standard", 1, 1, 10.0);
        PricingRule savedRule = new PricingRule("Car Rate", "Standard", 1, 1, 10.0);
        
        when(pricingRuleRepository.findByVehicleType(1)).thenReturn(Optional.empty());
        when(pricingRuleRepository.save(any(PricingRule.class))).thenReturn(savedRule);

        // When
        PricingRuleDto result = pricingRuleService.createPricingRule(request);

        // Then
        assertNotNull(result);
        assertEquals("Car Rate", result.name());
        assertEquals("Standard", result.description());
        assertEquals(1, result.vehicleType());
        verify(pricingRuleRepository, times(1)).save(any(PricingRule.class));
    }

    @Test
    void createPricingRule_shouldThrowException_whenVehicleTypeExists() {
        // Given
        PricingRuleCreateRequest request = new PricingRuleCreateRequest("Car Rate", "Standard", 1, 1, 10.0);
        when(pricingRuleRepository.findByVehicleType(1)).thenReturn(Optional.of(new PricingRule()));

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> pricingRuleService.createPricingRule(request));
        verify(pricingRuleRepository, never()).save(any(PricingRule.class));
    }

    @Test
    void getPricingRuleById_shouldReturnDto_whenFound() {
        // Given
        PricingRule entity = new PricingRule("Car Rate", "Standard", 1, 1, 10.0);
        when(pricingRuleRepository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        PricingRuleDto result = pricingRuleService.getPricingRuleById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Car Rate", result.name());
        verify(pricingRuleRepository, times(1)).findById(1L);
    }
    
    @Test
    void deletePricingRule_shouldSucceed() {
        // Given
        when(pricingRuleRepository.existsById(1L)).thenReturn(true);
        
        // When
        pricingRuleService.deletePricingRule(1L);
        
        // Then
        verify(pricingRuleRepository, times(1)).existsById(1L);
        verify(pricingRuleRepository, times(1)).deleteById(1L);
    }
}