package com.cmpe275lab2.dao;

import java.util.List;
import java.util.Optional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import com.cmpe275lab2.model.Player;
import com.cmpe275lab2.model.Sponsor;
import com.cmpe275lab2.repository.PlayerRepository;
import com.cmpe275lab2.repository.SponsorRepository;

@Service
public class PlayerDAO {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	SponsorRepository sponsorRepository;
	

	public Player save(Player player, String name) {
		boolean checkSponsor = sponsorRepository.existsByName(name);
		List<Sponsor> sponsor;
		if(checkSponsor) {
			sponsor = sponsorRepository.findByName(name);
			for(Sponsor s : sponsor) {
				player.setSponsor(s);
			}
			
		}
		return playerRepository.save(player);
	}
	
	public boolean sponsorExists(String name) {
		return sponsorRepository.existsByName(name);
	}
	
	public boolean emailExists(String email) {
		return playerRepository.existsByEmail(email);
	}
	
	public Player findPlayer(String id) {
		Long playerid = Long.parseLong(id);
		return playerRepository.findById(playerid).orElse(null);
	}

}
