package com.cmpe275lab2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="sponsor")
@EntityListeners(AuditingEntityListener.class)
public class Sponsor {

	@Id
	@NotBlank
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Embedded
	private Address address;

	@JsonIgnoreProperties(value = {"sponsor" , "opponents"})
	@OneToMany(mappedBy="sponsor", fetch = FetchType.EAGER)
	private List<Player> beneficiaries;	

	
	public List<Player> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(List<Player> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
	
}
