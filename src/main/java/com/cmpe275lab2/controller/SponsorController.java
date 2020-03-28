package com.cmpe275lab2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275lab2.dao.SponsorDAO;
import com.cmpe275lab2.model.Address;
import com.cmpe275lab2.model.Player;
import com.cmpe275lab2.model.Sponsor;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {
	
	@Autowired
	SponsorDAO sponsorDAO;
	
	@PostMapping(produces = { "application/json", "application/xml" })
	public Sponsor createSponsor(@Valid @RequestParam(name = "name") String name,
										@RequestParam(name = "description", required = false) String description,
										@RequestParam(name = "street", required = false) String street,
										@RequestParam(name = "city", required = false) String city,
										@RequestParam(name = "state", required = false) String state,
										@RequestParam(name = "zip", required = false) String zip
										) {
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);
		
		Sponsor sponsor = new Sponsor();
		sponsor.setName(name);
		sponsor.setDescription(description);
		sponsor.setAddress(address);
		
		return sponsorDAO.save(sponsor);
	}
	
	@GetMapping(value="/{name}",produces = { "application/json", "application/xml" })
	public ResponseEntity<Sponsor> getSponsor(@PathVariable(name = "name") String name) {
		Sponsor retrieved = sponsorDAO.findSponsor(name);
		if(retrieved == null) {
			return new ResponseEntity<Sponsor>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Sponsor>(retrieved, HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity updateSponsor(@Valid
			@PathVariable(name = "name") String name,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "zip", required = false) String zip
			){
		try {
			
			if(name.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name of sponsor is missing");
			}
			name = name.trim();
			
			if (description != null) {
				description = description.trim();				
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
			
//			List<Player> beneficiaries = new ArrayList<Player>();
			Address address = new Address();
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setZip(zip);
			
			Sponsor sponsor = new Sponsor();
			sponsor.setName(name);
			sponsor.setDescription(description);
			sponsor.setAddress(address);
//			sponsor.setBeneficiaries(beneficiaries);
			
			Sponsor result = sponsorDAO.updateSponsor(sponsor);
			
			if(result == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Sponsor name");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping(value="/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity deleteSponsor(@Valid @PathVariable(name = "name") String name) {
		try {
			if(name == null) {
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required params are missing");
			}
			name = name.trim();
			
			if (sponsorDAO.findSponsor(name) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sponsor name is invalid");
			}
			if (sponsorDAO.arePlayersBenefiting(name)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete sponsor. Players are benefiting from this sponsor");
			}
			Sponsor status = sponsorDAO.deleteSponsor(name);
			if(status == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Sponsor Name");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(status);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
