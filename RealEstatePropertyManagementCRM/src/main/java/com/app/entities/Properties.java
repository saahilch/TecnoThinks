package com.app.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.app.Enum.Status;

@Entity
@Table(name = "properties")
public class Properties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Property Name Cannot Blank")
	@Size(max = 100, message = "Property name must not exceed with 100 characters")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Location Name Cannot Blank")
	@Size(max = 200, message = "Loaction name must not exceed with 200 characters")
	@Column(nullable = false)
	private String location;

	private boolean availability;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "manager_id", nullable = false)
	private User manager;

	public Properties() {
		super();
	}

	public Properties(Long id, String name, String location, boolean availability, Status status, BigDecimal price,
			User manager) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.availability = availability;
		this.status = status;
		this.price = price;
		this.manager = manager;
	}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

}
