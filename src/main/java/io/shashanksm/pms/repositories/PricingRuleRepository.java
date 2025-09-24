package io.shashanksm.pms.repositories;

import io.shashanksm.pms.entities.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {

    /**
     * Custom finder method to retrieve an active pricing rule 
     * based on the vehicle type.
     * * Spring Data JPA automatically generates the query:
     * SELECT pr FROM PricingRule pr WHERE pr.vehicleType = ?1 AND pr.active = TRUE
     */
    Optional<PricingRule> findByVehicleTypeAndActiveTrue(Integer vehicleType);

    /**
     * Custom finder method to retrieve all active pricing rules.
     */
    List<PricingRule> findByActiveTrue();
    
    public Optional<PricingRule> findByVehicleType(Integer vehicleType);

	
}