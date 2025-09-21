package io.shashanksm.pms.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_lot_entry_gate")
public class ParkingLotEntryGate {

	@Column(name = "gate_number")
	private int gateNumber;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_lot", nullable = false)
	private ParkingLot parkingLot;

	public ParkingLotEntryGate() {
		super();

	}

	public ParkingLotEntryGate(long id, int gateNumber, ParkingLot parkingLot) {
		super();
		this.gateNumber = gateNumber;
		this.parkingLot = parkingLot;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingLotEntryGate other = (ParkingLotEntryGate) obj;
		return id == other.id;
	}

	public int getGateNumber() {
		return gateNumber;
	}

	public long getId() {
		return id;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void setGateNumber(int gateNumber) {
		this.gateNumber = gateNumber;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	@Override
	public String toString() {
		return "ParkingLotEntryGate [gateNumber=" + gateNumber + ", id=" + id + ", parkingLot=" + parkingLot + "]";
	}

}
