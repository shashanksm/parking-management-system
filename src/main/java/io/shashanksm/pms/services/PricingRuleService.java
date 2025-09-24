package io.shashanksm.pms.services;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.PricingRuleCreateRequest;
import io.shashanksm.pms.dtos.PricingRuleDto;
import io.shashanksm.pms.entities.PricingRule;
import io.shashanksm.pms.repositories.PricingRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PricingRuleService {

    private final PricingRuleRepository pricingRuleRepository;

    public PricingRuleService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    /**
     * Creates a new pricing rule.
     * @param request The DTO containing the pricing rule details.
     * @return A DTO of the newly created pricing rule.
     * @throws DuplicateResourceException if a rule for the same vehicle type already exists.
     */
    @Transactional
    public PricingRuleDto createPricingRule(PricingRuleCreateRequest request) {
        if (pricingRuleRepository.findByVehicleType(request.vehicleType()).isPresent()) {
            throw new DuplicateResourceException("A pricing rule for vehicle type " + request.vehicleType() + " already exists.");
        }

        PricingRule pricingRule = new PricingRule();
        pricingRule.setName(request.name());
        pricingRule.setDescription(request.description());
        pricingRule.setVehicleType(request.vehicleType());
        pricingRule.setFreeParkingHours(request.freeParkingHours());
        pricingRule.setHourlyRate(request.hourlyRate());
        
        // These fields are handled by the entity's default constructor, but we can set them explicitly for clarity
        pricingRule.setActive(true);
        pricingRule.setCreatedAt(Instant.now());

        PricingRule savedRule = pricingRuleRepository.save(pricingRule);
        return PricingRuleDto.fromEntity(savedRule);
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Retrieves a pricing rule by its ID.
     * @param id The ID of the pricing rule.
     * @return A DTO of the found pricing rule.
     * @throws ResourceNotFoundException if the rule is not found.
     */
    @Transactional(readOnly = true)
    public PricingRuleDto getPricingRuleById(Long id) {
        PricingRule pricingRule = pricingRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PricingRule not found with ID: " + id));
        return PricingRuleDto.fromEntity(pricingRule);
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Retrieves all pricing rules.
     * @return A list of all pricing rule DTOs.
     */
    @Transactional(readOnly = true)
    public List<PricingRuleDto> getAllPricingRules() {
        return pricingRuleRepository.findAll().stream()
            .map(PricingRuleDto::fromEntity)
            .collect(Collectors.toList());
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Updates an existing pricing rule.
     * @param id The ID of the rule to update.
     * @param request The DTO with the updated details.
     * @return A DTO of the updated pricing rule.
     * @throws ResourceNotFoundException if the rule is not found.
     * @throws DuplicateResourceException if the updated vehicle type conflicts with an existing rule.
     */
    @Transactional
    public PricingRuleDto updatePricingRule(Long id, PricingRuleCreateRequest request) {
        PricingRule existingRule = pricingRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PricingRule not found with ID: " + id));

        // Check for duplicate vehicle type if it's being changed
        if (!existingRule.getVehicleType().equals(request.vehicleType())) {
            if (pricingRuleRepository.findByVehicleType(request.vehicleType()).isPresent()) {
                throw new DuplicateResourceException("A pricing rule for vehicle type " + request.vehicleType() + " already exists.");
            }
        }
        
        existingRule.setName(request.name());
        existingRule.setDescription(request.description());
        existingRule.setVehicleType(request.vehicleType());
        existingRule.setFreeParkingHours(request.freeParkingHours());
        existingRule.setHourlyRate(request.hourlyRate());
        
        PricingRule updatedRule = pricingRuleRepository.save(existingRule);
        return PricingRuleDto.fromEntity(updatedRule);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Deletes a pricing rule by its ID.
     * @param id The ID of the pricing rule to delete.
     * @throws ResourceNotFoundException if the rule is not found.
     */
    @Transactional
    public void deletePricingRule(Long id) {
        if (!pricingRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("PricingRule not found with ID: " + id);
        }
        pricingRuleRepository.deleteById(id);
    }
}