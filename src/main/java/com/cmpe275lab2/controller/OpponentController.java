package com.cmpe275lab2.controller;

import java.util.*;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275lab2.dao.OpponentDAO;
import com.cmpe275lab2.model.Player;

@RestController
@RequestMapping("/opponents")
public class OpponentController {
	
	
	@Autowired
	OpponentDAO opponentDAO;
	
	
	@PostMapping(value = "/{id1}/{id2}", produces = { "application/json", "application/xml" })
	public ResponseEntity addOpponents(@Valid   
			@PathVariable(name = "id1") String id1,
			@PathVariable(name = "id2") String id2
		) 	{
		
		try {
			try{
	            Long l1 = Long.parseLong(id1);
	            Long l2 = Long.parseLong(id2);
	        } catch (NumberFormatException e) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid player ID");
	        }
			Player p1=opponentDAO.findPlayer(id1);
			Player p2=opponentDAO.findPlayer(id2);
			if(p1==null || p2==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atleast one of the player ID is Invalid");
			}
			
			if(id1.equals(id2)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player cannot be opponent of themself");	
			}
			opponentDAO.save(p1,p2);
			return ResponseEntity.status(HttpStatus.OK).body("Opponent added");	
		
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(value = "/{id1}/{id2}", produces = { "application/json", "application/xml" })
	public ResponseEntity removeOpponents(@Valid   
			@PathVariable(name = "id1") String id1,
			@PathVariable(name = "id2") String id2) {
		
		try{
			try{
	            Long l1 = Long.parseLong(id1);
	            Long l2 = Long.parseLong(id2);
	        } catch (NumberFormatException e) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid player ID");
	        }
			
			Player p1=opponentDAO.findPlayer(id1);
			Player p2=opponentDAO.findPlayer(id2);
			if(p1==null || p2==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atleast one of the player ID is Invalid");
			}
			
			if(id1.equals(id2)){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Opponents cannot be the same");	
			}
			if(!opponentDAO.areOpponents(id1, id2)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The two players are not opponents");
			}
			opponentDAO.unsave(p1,p2);
			return ResponseEntity.status(HttpStatus.OK).body("Opponent deleted");	
		
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


}
