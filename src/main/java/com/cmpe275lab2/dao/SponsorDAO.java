package com.cmpe275lab2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275lab2.model.Sponsor;
import com.cmpe275lab2.repository.SponsorRepository;

@Service
public class SponsorDAO {
	
	@Autowired
	SponsorRepository sponsorRepository;
	
	// Create a Sponsor
	public Sponsor save(Sponsor sponsor) {
		return sponsorRepository.save(sponsor);
	}
	
	
	// Find a Sponsor
	public Sponsor findSponsor(String name) {
		List<Sponsor> sponsor = sponsorRepository.findByName(name);
		if(sponsor.size() > 0) {
			return sponsor.get(0);
		} else {
			return null;
		}
	}
	
	// Update a Sponsor
	public Sponsor updateSponsor(Sponsor sponsor) {
		List<Sponsor> sponsorList = sponsorRepository.findByName(sponsor.getName());
		Sponsor sponsorObj = null;
		if(sponsorList.size() > 0) {
			sponsorObj = sponsorList.get(0);
			
			sponsorObj.setName(sponsor.getName());
			sponsorObj.setDescription(sponsor.getDescription());
			sponsorObj.setAddress(sponsor.getAddress());
			
			return sponsorRepository.save(sponsorObj);
		} else {
			return null;
		}
	}
	
	// Delete a Sponsor
	public Sponsor deleteSponsor(String name) {
		List<Sponsor> sponsorList = sponsorRepository.findByName(name);
		Sponsor sponsorObj = null;
		if(sponsorList.size() > 0) {
			sponsorObj = sponsorList.get(0);
			sponsorRepository.delete(sponsorObj);
			return sponsorObj;
		} else {
			return null;
		}
	}
}
