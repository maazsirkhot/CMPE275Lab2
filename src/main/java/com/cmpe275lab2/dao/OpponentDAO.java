package com.cmpe275lab2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275lab2.model.Player;

import com.cmpe275lab2.repository.PlayerRepository;

@Service
public class OpponentDAO {
	
	@Autowired
	PlayerRepository playerRepository;
	
	public Player save(Player p1,Player p2) {
		//Saving p1->p2
		List<Player> opponentsOfP1;
		opponentsOfP1=p1.getOpponents();
		if (opponentsOfP1.contains(p2)) {
			return null;
		}
		opponentsOfP1.add(p2);
		p1.setOpponent(opponentsOfP1);
		playerRepository.save(p1);
	
		//saving p2->p1
		List<Player> opponentsOfP2;
		opponentsOfP2=p2.getOpponents();
		opponentsOfP2.add(p1);
		p2.setOpponent(opponentsOfP2);
		playerRepository.save(p2);

		return playerRepository.save(p1);
	}
	
	public Player unsave(Player p1,Player p2) {
		//removing p1->p2
		List<Player> opponentsOfP1;
		opponentsOfP1=p1.getOpponents();
		opponentsOfP1.remove(p2);
		p1.setOpponent(opponentsOfP1);
		playerRepository.save(p1);
	
		//removing p2->p1
		List<Player> opponentsOfP2;
		opponentsOfP2=p2.getOpponents();
		opponentsOfP2.remove(p1);
		p2.setOpponent(opponentsOfP2);
		playerRepository.save(p2);
	
		return playerRepository.save(p1);
	}
	
	public Player findPlayer(String id) {	
		Long playerid = Long.parseLong(id);
		return playerRepository.findById(playerid).orElse(null);
	}
	
	public boolean isValidPlayerId(String id) {
		Long playerid = Long.parseLong(id);
		System.out.println(playerRepository.existsById(playerid));
		return playerRepository.existsById(playerid);
	}
	
	public boolean areOpponents(String p1, String p2) {
		Long playerid2 = Long.parseLong(p2);
		Player playerObj = findPlayer(p1);
		List<Player> opponentsOfPlayer = playerObj.getOpponents();
		for (Player opponent: opponentsOfPlayer) {
			if (opponent.getPlayerId() == playerid2) {
				return true;
			}
		}
		return false;
	}
	
	public void removeOpponents(String id) {
		Long playerid = Long.parseLong(id);
		Player playerToBeRemoved = findPlayer(id);
		List<Player> opponentsOfPlayer;
		opponentsOfPlayer = playerToBeRemoved.getOpponents();
		
		List<Player> tempOpponents;
		for (Player opponent: opponentsOfPlayer) {
			System.out.println(opponent.getPlayerId());
			tempOpponents = opponent.getOpponents();
			tempOpponents.remove(playerToBeRemoved);
			opponent.setOpponent(tempOpponents);
			playerRepository.save(opponent);
		}
		
		opponentsOfPlayer = new ArrayList<Player>();
		playerRepository.save(playerToBeRemoved);
				
	}
	
}
