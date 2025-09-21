package io.shashanksm.pms.entities;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pricing_rule")
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description") // Maps to TEXT
    private String description;

    @Column(name = "vehicle_type", nullable = false)
    private Integer vehicleType; // Use Integer if mapping to an Enum might be needed later

    @Column(name = "free_parking_hours", nullable = false)
    private Integer freeParkingHours;

    @Column(name = "hourly_rate", nullable = false)
    private Double hourlyRate; // Use Double for DOUBLE PRECISION

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // --- Constructors ---

    // Required by JPA
    public PricingRule() {
        this.active = true;
        this.freeParkingHours = 0;
        this.createdAt = Instant.now();
    }

    // Constructor for creating a new rule (without ID)
    public PricingRule(String name, String description, Integer vehicleType, Integer freeParkingHours, Double hourlyRate) {
        this(); // Initialize defaults
        this.name = name;
        this.description = description;
        this.vehicleType = vehicleType;
        this.freeParkingHours = freeParkingHours;
        this.hourlyRate = hourlyRate;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getFreeParkingHours() {
        return freeParkingHours;
    }

    public void setFreeParkingHours(Integer freeParkingHours) {
        this.freeParkingHours = freeParkingHours;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // --- Standard equals and hashCode (using ID) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PricingRule)) return false;
        return id != null && Objects.equals(id, ((PricingRule) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}