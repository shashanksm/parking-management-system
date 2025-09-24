package io.shashanksm.pms.api;

import io.shashanksm.pms.business.exceptions.DuplicateResourceException;
import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateDistanceCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDistanceDto;
import io.shashanksm.pms.dtos.EntryGateDistanceUpdateRequest;
import io.shashanksm.pms.services.EntryGateDistanceService;
import io.shashanksm.pms.services.EntryGateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances")
public class EntryGateDistanceController {

    private final EntryGateDistanceService entryGateDistanceService;
    private final EntryGateService entryGateService;

    public EntryGateDistanceController(
            EntryGateDistanceService entryGateDistanceService,
            EntryGateService entryGateService) {
        this.entryGateDistanceService = entryGateDistanceService;
        this.entryGateService = entryGateService;
    }

    /**
     * Creates a new distance record for a specific entry gate.
     * POST /api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances
     */
    @PostMapping
    public ResponseEntity<EntryGateDistanceDto> createDistance(
            @PathVariable Long parkingLotId,
            @PathVariable Long entryGateId,
            @RequestBody EntryGateDistanceCreateRequest request) {
        try {
            // Verify the parent resources exist
            if (!entryGateService.getEntryGateById(entryGateId).parkingLotId().equals(parkingLotId)) {
                throw new ResourceNotFoundException("EntryGate with ID " + entryGateId + " not found for ParkingLot with ID " + parkingLotId);
            }

            EntryGateDistanceDto createdDistance = entryGateDistanceService.create(new EntryGateDistanceCreateRequest(
                entryGateId,
                request.parkingSlotId(),
                request.distance()
            ));

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdDistance.id())
                    .toUri();

            return ResponseEntity.created(location).body(createdDistance);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Retrieves all distance records for a specific entry gate.
     * GET /api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances
     */
    @GetMapping
    public ResponseEntity<List<EntryGateDistanceDto>> getAllDistances(
            @PathVariable Long parkingLotId,
            @PathVariable Long entryGateId) {
        try {
            // Verify the parent resources exist
            if (!entryGateService.getEntryGateById(entryGateId).parkingLotId().equals(parkingLotId)) {
                throw new ResourceNotFoundException("EntryGate with ID " + entryGateId + " not found for ParkingLot with ID " + parkingLotId);
            }

            List<EntryGateDistanceDto> distances = entryGateDistanceService.findAll().stream()
                .filter(d -> d.parkingLotEntryGateId().equals(entryGateId))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(distances);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a single distance record by its ID.
     * GET /api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances/{distanceId}
     */
    @GetMapping("/{distanceId}")
    public ResponseEntity<EntryGateDistanceDto> getDistanceById(
            @PathVariable Long parkingLotId,
            @PathVariable Long entryGateId,
            @PathVariable Long distanceId) {
        try {
            // Verify the parent resources exist and the child resource is correctly nested
            EntryGateDistanceDto distance = entryGateDistanceService.findById(distanceId);
            if (!distance.parkingLotEntryGateId().equals(entryGateId) || !entryGateService.getEntryGateById(entryGateId).parkingLotId().equals(parkingLotId)) {
                throw new ResourceNotFoundException("Distance record not found with ID " + distanceId + " for the specified entry gate and parking lot.");
            }
            return ResponseEntity.ok(distance);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates an existing distance record.
     * PUT /api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances/{distanceId}
     */
    @PutMapping("/{distanceId}")
    public ResponseEntity<EntryGateDistanceDto> updateDistance(
            @PathVariable Long parkingLotId,
            @PathVariable Long entryGateId,
            @PathVariable Long distanceId,
            @RequestBody EntryGateDistanceUpdateRequest request) {
        try {
            // Verify parent resources and child resource ownership
            EntryGateDistanceDto existingDistance = entryGateDistanceService.findById(distanceId);
            if (!existingDistance.parkingLotEntryGateId().equals(entryGateId) || !entryGateService.getEntryGateById(entryGateId).parkingLotId().equals(parkingLotId)) {
                throw new ResourceNotFoundException("Distance record not found with ID " + distanceId + " for the specified entry gate and parking lot.");
            }
            
            // Set the correct IDs for the update request
            EntryGateDistanceUpdateRequest validatedRequest = new EntryGateDistanceUpdateRequest(
                distanceId,
                entryGateId,
                request.parkingSlotId(),
                request.distance()
            );

            EntryGateDistanceDto updatedDistance = entryGateDistanceService.update(validatedRequest);
            return ResponseEntity.ok(updatedDistance);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Deletes a distance record.
     * DELETE /api/admin/parking-lots/{parkingLotId}/entry-gates/{entryGateId}/distances/{distanceId}
     */
    @DeleteMapping("/{distanceId}")
    public ResponseEntity<Void> deleteDistance(
            @PathVariable Long parkingLotId,
            @PathVariable Long entryGateId,
            @PathVariable Long distanceId) {
        try {
            // Verify parent resources and child resource ownership
            EntryGateDistanceDto distance = entryGateDistanceService.findById(distanceId);
            if (!distance.parkingLotEntryGateId().equals(entryGateId) || !entryGateService.getEntryGateById(entryGateId).parkingLotId().equals(parkingLotId)) {
                throw new ResourceNotFoundException("Distance record not found with ID " + distanceId + " for the specified entry gate and parking lot.");
            }

            entryGateDistanceService.delete(distanceId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}