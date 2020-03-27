package com.cmpe275lab2.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	SponsorRepository sponsorRepository;
	
	@PostMapping
	public ResponseEntity createPlayer(@Valid   
										@RequestParam(name = "firstname") String firstname,
										@RequestParam(name = "lastname") String lastname,
										@RequestParam(name = "email") String email,
										@RequestParam(name = "description", required = false) String description,
										@RequestParam(name = "street", required = false ) String street,
										@RequestParam(name = "city", required = false) String city,
										@RequestParam(name = "state", required = false) String state,
										@RequestParam(name = "zip", required = false) String zip,
										@RequestParam(name = "sponsorname", required = false) String sponsor
		) {
		
		
		try {
			
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
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sponsor does not exist");
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
			
			Player player = new Player();
			player.setAddress(address);
			player.setFirstname(firstname);
			player.setLastname(lastname);
			player.setEmail(email);
			player.setDescription(description);
			
			return ResponseEntity.status(HttpStatus.OK).body(playerDAO.save(player, sponsor));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findPlayer(@Valid @PathVariable(name = "id") String id) {
		try {
			id = id.trim();
			Player player = playerDAO.findPlayer(id);
			if (player == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid player ID");
			}
			return ResponseEntity.status(HttpStatus.OK).body(player);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updatePlayer(@Valid 
										@PathVariable(name = "id") String id,
										@RequestParam(name = "firstname") String firstname,
										@RequestParam(name = "lastname") String lastname,
										@RequestParam(name = "email") String email,
										@RequestParam(name = "description", required = false) String description,
										@RequestParam(name = "street", required = false ) String street,
										@RequestParam(name = "city", required = false) String city,
										@RequestParam(name = "state", required = false) String state,
										@RequestParam(name = "zip", required = false) String zip,
										@RequestParam(name = "sponsorname", required = false) String sponsor
									) {
		try {
			id = id.trim();
			
			if (!playerDAO.isValidPlayerId(id)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid player ID");
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
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sponsor does not exist");
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
			
			if(playerDAO.isDuplicateEmail(email, id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
			}
			
			Player player = playerDAO.findPlayer(id);

			
			Address address = new Address();
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setZip(zip);
			
			player.setAddress(address);
			player.setFirstname(firstname);
			player.setLastname(lastname);
			player.setEmail(email);
			player.setDescription(description);
			
			return ResponseEntity.status(HttpStatus.OK).body(playerDAO.save(player, sponsor));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	

	
	@DeleteMapping("/{id}")
	public ResponseEntity deletePlayer(@Valid @PathVariable(name = "id") String id) {
		try {
			id = id.trim();
			Player player = playerDAO.findPlayer(id);
			if (player == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid player ID");
			}
			playerDAO.deletePlayer(id);
			return ResponseEntity.status(HttpStatus.OK).body(player);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
