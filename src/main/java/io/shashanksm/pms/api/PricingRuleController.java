package io.shashanksm.pms.api;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.PricingRuleCreateRequest;
import io.shashanksm.pms.dtos.PricingRuleDto;
import io.shashanksm.pms.services.PricingRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/pricing-rules")
public class PricingRuleController {

    private final PricingRuleService pricingRuleService;

    public PricingRuleController(PricingRuleService pricingRuleService) {
        this.pricingRuleService = pricingRuleService;
    }

    /**
     * Creates a new pricing rule.
     * POST /api/admin/pricing-rules
     */
    @PostMapping
    public ResponseEntity<PricingRuleDto> createPricingRule(@RequestBody PricingRuleCreateRequest request) {
        try {
            PricingRuleDto createdRule = pricingRuleService.createPricingRule(request);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdRule.id())
                    .toUri();
            return ResponseEntity.created(location).body(createdRule);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Retrieves all pricing rules.
     * GET /api/admin/pricing-rules
     */
    @GetMapping
    public ResponseEntity<List<PricingRuleDto>> getAllPricingRules() {
        List<PricingRuleDto> rules = pricingRuleService.getAllPricingRules();
        return ResponseEntity.ok(rules);
    }

    /**
     * Retrieves a single pricing rule by its ID.
     * GET /api/admin/pricing-rules/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PricingRuleDto> getPricingRuleById(@PathVariable Long id) {
        try {
            PricingRuleDto rule = pricingRuleService.getPricingRuleById(id);
            return ResponseEntity.ok(rule);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates an existing pricing rule.
     * PUT /api/admin/pricing-rules/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PricingRuleDto> updatePricingRule(@PathVariable Long id, @RequestBody PricingRuleCreateRequest request) {
        try {
            PricingRuleDto updatedRule = pricingRuleService.updatePricingRule(id, request);
            return ResponseEntity.ok(updatedRule);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Deletes a pricing rule by its ID.
     * DELETE /api/admin/pricing-rules/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePricingRule(@PathVariable Long id) {
        try {
            pricingRuleService.deletePricingRule(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}