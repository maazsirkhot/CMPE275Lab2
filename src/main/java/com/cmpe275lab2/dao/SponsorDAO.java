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
	
	public Sponsor save(Sponsor sponsor) {
		return sponsorRepository.save(sponsor);
	}
	
	public Sponsor findSponsor(String name) {
		List<Sponsor> sponsor = sponsorRepository.findByName(name);
		Sponsor  sponsorObj = null;
		if(sponsor.size() > 0) {
			sponsorObj = sponsor.get(0);
		}
		//System.out.println(sponsor.getPlayers());
		return sponsorObj;
	}
}
