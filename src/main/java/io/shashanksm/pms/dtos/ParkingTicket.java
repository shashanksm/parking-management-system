package io.shashanksm.pms.dtos;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable class representing a parking ticket/session summary.
 */
public final class ParkingTicket {

    private final Long id;
    private final String owner;
    private final String vehicleNumber;
    private final String parkingLot;
    private final int entryGate;
    private final int slotNumber;
    private final Instant entryTime;

    /**
     * Private constructor enforces creation via the static Builder.
     */
    private ParkingTicket(Builder builder) {
        this.id = builder.id;
        this.owner = builder.owner;
        this.vehicleNumber = builder.vehicleNumber;
        this.parkingLot = builder.parkingLot;
        this.entryGate = builder.entryGate;
        this.slotNumber = builder.slotNumber;
        this.entryTime = builder.entryTime;
    }

    /**
     * Factory method to start the building process.
     */
    public static Builder builder() {
        return new Builder();
    }

    // --- Accessor Methods (Getters) ---
    // Note: No setters, ensuring immutability.

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public int getEntryGate() {
        return entryGate;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    // --- Standard Overrides ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTicket that = (ParkingTicket) o;
        // Equals is generally based on the ID for DTOs
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", parkingLot='" + parkingLot + '\'' +
                ", entryTime=" + entryTime +
                '}';
    }

    // ========================================================================
    // BUILDER CLASS
    // ========================================================================

    public static class Builder {
        private Long id;
        private String owner;
        private String vehicleNumber;
        private String parkingLot;
        private int entryGate;
        private int slotNumber;
        private Instant entryTime;

        // Private constructor ensures instantiation only via ParkingTicket.builder()
        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder vehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
            return this;
        }

        public Builder parkingLot(String parkingLot) {
            this.parkingLot = parkingLot;
            return this;
        }

        public Builder entryGate(int entryGate) {
            this.entryGate = entryGate;
            return this;
        }

        public Builder slotNumber(int slotNumber) {
            this.slotNumber = slotNumber;
            return this;
        }

        public Builder entryTime(Instant entryTime) {
            this.entryTime = entryTime;
            return this;
        }

        /**
         * Final build method that constructs the immutable ParkingTicket instance.
         */
        public ParkingTicket build() {
            // Basic validation check
            if (id == null || vehicleNumber == null || entryTime == null) {
                throw new IllegalStateException("Ticket ID, Vehicle Number, and Entry Time must be set.");
            }
            
            return new ParkingTicket(this);
        }
    }
}