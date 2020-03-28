package com.cmpe275lab2.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="player")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement
public class Player {

	public Player() {
		this.address = new Address();
	}
	
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

	@ManyToOne
	@JoinColumn(name = "sponsorname")
	@JsonIgnoreProperties("beneficiaries")
	private Sponsor sponsor;

	
	// Many to One
	@JsonIgnoreProperties({"opponents"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name="opponents",
		joinColumns={@JoinColumn(name="player1")},
		inverseJoinColumns={@JoinColumn(name="player2")})
	private List<Player> opponents = new ArrayList<Player>();

	public void setOpponent(List<Player> opponents) {
		this.opponents = opponents;
	}
	public List<Player> getOpponents() {
		return opponents;
	}
	
	
	//ends here
	
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
