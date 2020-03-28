package com.cmpe275lab2.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="sponsor")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement
public class Sponsor {
	
	public Sponsor() {
		this.address = new Address();
	}

	@Id
	@NotBlank
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Embedded
	@AttributeOverrides(
			value = {
					@AttributeOverride(name = "street", column = @Column(name = "street")),
					@AttributeOverride(name = "city", column = @Column(name = "city")),
					@AttributeOverride(name = "state", column = @Column(name = "state")),
					@AttributeOverride(name = "zip", column = @Column(name = "zipcode"))
			}
	)
	private Address address;

	@JsonIgnoreProperties(value = {"sponsor" , "opponents"})
	@OneToMany(mappedBy="sponsor", fetch = FetchType.EAGER)
	private List<Player> beneficiaries = new ArrayList<Player>();	

	
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
