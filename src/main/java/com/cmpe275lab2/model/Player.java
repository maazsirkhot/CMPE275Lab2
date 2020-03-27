package com.cmpe275lab2.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="player")
@EntityListeners(AuditingEntityListener.class)

public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "player_id")
	private Long playerId;
	
	@NotBlank
	@Column(name = "firstname")
	private String firstname;
	
	@NotBlank
	@Column(name = "lastname")
	private String lastname;
	
	@NotBlank
	@Column(name = "email")
	private String email;
	
	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sponsorname")
	@JsonIgnoreProperties("beneficiaries")
	private Sponsor sponsor;

	@Embedded 
	private Address address;

	
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Sponsor getSponsor() {
		return sponsor;

	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	public Address getAddress() { 
		return address; 
	} 
	public void setAddress(Address address) { 
		this.address = address; 
	}

	
}
