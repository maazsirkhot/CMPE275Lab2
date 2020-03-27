package com.cmpe275lab2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275lab2.dao.SponsorDAO;
import com.cmpe275lab2.model.Address;
import com.cmpe275lab2.model.Sponsor;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {
	
	@Autowired
	SponsorDAO sponsorDAO;
	
	@PostMapping
	public Sponsor createSponsor(@Valid @RequestParam(name = "name") String name,
										@RequestParam(name = "description", required = false, defaultValue = "N/A") String description,
										@RequestParam(name = "street", required = false, defaultValue = "N/A") String street,
										@RequestParam(name = "city", required = false, defaultValue = "N/A") String city,
										@RequestParam(name = "state", required = false, defaultValue = "N/A") String state,
										@RequestParam(name = "zip", required = false, defaultValue = "N/A") String zip
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
	
	@GetMapping("/{name}")
	public Sponsor getSponsor(@PathVariable(name = "name") String name) {
		return sponsorDAO.findSponsor(name);
	}
}
