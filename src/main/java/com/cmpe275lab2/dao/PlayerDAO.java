package com.cmpe275lab2.dao;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	

	public Player save(Player player, String sponsorName) {
		if (sponsorName != null) {
			List<Sponsor> sponsor;
			sponsor = sponsorRepository.findByName(sponsorName);
			for(Sponsor s : sponsor) {
				player.setSponsor(s);
			}
		} else {
			player.setSponsor(null);
		}
		return playerRepository.save(player);
	}
	
	public boolean sponsorExists(String name) {
		return sponsorRepository.existsByName(name);
	}
	
	public boolean emailExists(String email) {
		return playerRepository.existsByEmail(email);
	}
	
	public boolean isValidPlayerId(String id) {
		Long playerid = Long.parseLong(id);
		return playerRepository.existsById(playerid);
	}
	
	public Player findPlayer(String id) {
		Long playerid = Long.parseLong(id);
		return playerRepository.findById(playerid).orElse(null);
	}
	
	public boolean isDuplicateEmail(String email, String id) {
		Long playerid = Long.parseLong(id);
		List <Player> player = playerRepository.findByEmail(email);
		for (Player p : player) {
			if (p.getId() != playerid) {
				return true;
			}
		}
		return false;
	}
	
	public void deletePlayer(String id) {
		Long playerid = Long.parseLong(id);
		playerRepository.deleteById(playerid);
	}
	
}
