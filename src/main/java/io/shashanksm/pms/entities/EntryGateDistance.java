package io.shashanksm.pms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
// The unique constraint mirrors the DDL to prevent duplicate gate-slot pairings
@Table(name = "entry_gate_distance", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"parking_lot_entry_gate", "parking_slot"}, name = "uq_gate_slot")
})
public class EntryGateDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // --- Relationship to ParkingLotEntryGate ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_lot_entry_gate", nullable = false)
    private ParkingLotEntryGate parkingLotEntryGate;

    // --- Relationship to ParkingSlot ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_slot", nullable = false)
    private ParkingSlot parkingSlot;

    @Column(name = "distance", nullable = false)
    private Integer distance; // Maps to INTEGER NOT NULL

    // --- Constructors ---

    // Required by JPA
    public EntryGateDistance() {
    }

    // Constructor for creating a new distance record (without ID)
    public EntryGateDistance(ParkingLotEntryGate parkingLotEntryGate, ParkingSlot parkingSlot, Integer distance) {
        this.parkingLotEntryGate = parkingLotEntryGate;
        this.parkingSlot = parkingSlot;
        this.distance = distance;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingLotEntryGate getParkingLotEntryGate() {
        return parkingLotEntryGate;
    }

    public void setParkingLotEntryGate(ParkingLotEntryGate parkingLotEntryGate) {
        this.parkingLotEntryGate = parkingLotEntryGate;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    // --- Standard equals and hashCode (using ID) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryGateDistance)) return false;
        return id != null && Objects.equals(id, ((EntryGateDistance) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}