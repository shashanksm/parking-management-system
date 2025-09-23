package io.shashanksm.pms.api;

import io.shashanksm.pms.business.exceptions.ResourceNotFoundException;
import io.shashanksm.pms.dtos.EntryGateCreateRequest;
import io.shashanksm.pms.dtos.EntryGateDto;
import io.shashanksm.pms.services.EntryGateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/parking-lots/{parkingLotId}/entry-gates")
public class EntryGateController {

    private final EntryGateService entryGateService;

    public EntryGateController(EntryGateService entryGateService) {
        this.entryGateService = entryGateService;
    }

    /**
     * Creates a new entry gate for a specific parking lot.
     * POST /api/admin/parking-lots/{parkingLotId}/entry-gates
     */
    @PostMapping
    public ResponseEntity<EntryGateDto> createEntryGate(
        @PathVariable Long parkingLotId,
        @RequestBody EntryGateCreateRequest request) {
        
        EntryGateCreateRequest validatedRequest = new EntryGateCreateRequest(
            parkingLotId,
            request.gateNumber()
        );
        
        EntryGateDto createdGate = entryGateService.createEntryGate(validatedRequest);
        return new ResponseEntity<>(createdGate, HttpStatus.CREATED);
    }

    /**
     * Retrieves all entry gates for a specific parking lot.
     * GET /api/admin/parking-lots/{parkingLotId}/entry-gates
     */
    @GetMapping
    public ResponseEntity<List<EntryGateDto>> getAllEntryGates(@PathVariable Long parkingLotId) {
        List<EntryGateDto> allGates = entryGateService.getAllEntryGates();
        List<EntryGateDto> filteredGates = allGates.stream()
                .filter(gate -> gate.parkingLotId().equals(parkingLotId))
                .toList();
        
        return ResponseEntity.ok(filteredGates);
    }
    
    // --- NEW METHOD ---

    /**
     * Deletes a specific entry gate from a parking lot.
     * DELETE /api/admin/parking-lots/{parkingLotId}/entry-gates/{gateId}
     */
    @DeleteMapping("/{gateId}")
    public ResponseEntity<Void> deleteEntryGate(
        @PathVariable Long parkingLotId,
        @PathVariable Long gateId) {
        
        // This check is a good practice to ensure the gate belongs to the correct parking lot
        try {
            EntryGateDto gateDto = entryGateService.getEntryGateById(gateId);
            if (!gateDto.parkingLotId().equals(parkingLotId)) {
                // If the gate's parent doesn't match the URL, throw a not found exception
                throw new ResourceNotFoundException("EntryGate with ID " + gateId + " not found under ParkingLot with ID " + parkingLotId);
            }
            entryGateService.deleteEntryGate(gateId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}