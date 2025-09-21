package io.shashanksm.pms.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	@Column(name = "owner")
	private String owner;
	
	@Column(name = "type")
	private int type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Vehicle(Long id, String vehicleNumber, String owner, int type) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.owner = owner;
		this.type = type;
	}

	public Vehicle() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(vehicleNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(vehicleNumber, other.vehicleNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", vehicleNumber=" + vehicleNumber + ", owner=" + owner + ", type=" + type + "]";
	}

	
}
