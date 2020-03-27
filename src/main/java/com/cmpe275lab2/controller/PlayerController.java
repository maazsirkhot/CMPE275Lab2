package com.cmpe275lab2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> master
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275lab2.dao.PlayerDAO;
import com.cmpe275lab2.model.Address;
import com.cmpe275lab2.model.Player;
import com.cmpe275lab2.model.Sponsor;
import com.cmpe275lab2.repository.SponsorRepository;
import com.cmpe275lab2.dao.SponsorDAO;
import com.cmpe275lab2.repository.PlayerRepository;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	PlayerDAO playerDAO;
<<<<<<< HEAD
	
	@Autowired
	SponsorRepository sponsorRepository;
	
	@PostMapping
	public Player createPlayer(@Valid @RequestParam(name = "firstname") String firstname,
			@RequestParam(name = "lastname") String lastname,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "street", required = false ) String street,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "zip", required = false) String zip,
			@RequestParam(name = "sponsorname", required = false) String name) {
		
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);
		
		Player player = new Player();
		player.setAddress(address);
		player.setFirstname(firstname);
		player.setLastname(lastname);
		player.setEmail(email);
		player.setDescription(description);
		
		return playerDAO.save(player, name);
	}
	
	@GetMapping("/{id}")
	public Player findPlayer(@PathVariable(name = "id") String id) {
		return playerDAO.findPlayer(id);
=======
		
	@PostMapping
	public ResponseEntity createPlayer(@Valid   @RequestParam(name = "firstname") String firstname,
										@RequestParam(name = "lastname") String lastname,
										@RequestParam(name = "email") String email,
										@RequestParam(name = "description", required = false) String description,
										@RequestParam(name = "sponsor", required = false) String sponsor,
										@RequestParam(name = "street", required = false) String street,
										@RequestParam(name = "city", required = false) String city,
										@RequestParam(name = "state", required = false) String state,
										@RequestParam(name = "zip", required = false) String zip,
										@RequestParam(name = "opponents", required = false) String opponents
			) {
//		try {
			
			if (opponents != null) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Opponents cannot be passed as parameters");
			}
			
			firstname = firstname.trim();
			lastname = lastname.trim();
			email = email.trim();
			
			if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required params are missing");
			}
			
			if (description != null) {
				description = description.trim();				
			}
			if (sponsor != null) {
				sponsor = sponsor.trim();	
				if (!playerDAO.sponsorExists(sponsor)) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Sponsor does not exist");
				}
			}
			if (street != null) {
				street = street.trim();				
			}
			if (city != null) {
				city = city.trim();				
			}
			if (state != null) {
				state = state.trim();				
			}
			if (zip != null) {
				zip = zip.trim();
			}
			
			if(playerDAO.emailExists(email)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
			}
			
			Address address = new Address();
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setZip(zip);
			
//			Sponsor sponsorObj = playerDAO.getSponsor(sponsor);
			Player player = new Player();
			player.setFirstname(firstname);
			player.setLastname(lastname);
			player.setEmail(email);
			player.setDescription(description);
//			player.setSponsorObj(playerDAO.getSponsor(sponsor));
			player.setAddress(address);
			
			
			return ResponseEntity.status(HttpStatus.OK).body(playerDAO.save(player, sponsor));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some error");
//		}
		
>>>>>>> master
	}
}
