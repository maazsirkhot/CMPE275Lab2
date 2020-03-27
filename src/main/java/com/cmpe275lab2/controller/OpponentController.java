package com.cmpe275lab2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275lab2.dao.OpponentDAO;
import com.cmpe275lab2.model.Player;

@RestController
@RequestMapping("/opponents")
public class OpponentController {
	
	
	@Autowired
	OpponentDAO opponentDAO;
	
	
	@PostMapping
	public ResponseEntity addOpponents(@Valid   
			@RequestParam(name = "id1") String id1,
			@RequestParam(name = "id2") String id2
			
) 	{
		
		try
		{
			
			Player p1=opponentDAO.findPlayer(id1);
			Player p2=opponentDAO.findPlayer(id2);
		if(p1==null|| p2==null )
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of the player ID is Invalid");
		}
		
		if(id1.equals(id2))
		{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Opponents cannot be the same");	
		}
		
	return ResponseEntity.status(HttpStatus.OK).body(opponentDAO.save(p1,p2));	
		
		}
		catch(Exception e)
		{
			System.out.println("Exception "+e);
		}
		return null;
	}
	
	
	@DeleteMapping
	public ResponseEntity removeOpponents(@Valid   
			@RequestParam(name = "id1") String id1,
			@RequestParam(name = "id2") String id2
			
) 	{
		
		try
		{
			
			Player p1=opponentDAO.findPlayer(id1);
			Player p2=opponentDAO.findPlayer(id2);
		if(p1==null|| p2==null )
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of the player ID is Invalid");
		}
		
		if(id1.equals(id2))
		{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Opponents cannot be the same");	
		}
		
	return ResponseEntity.status(HttpStatus.OK).body(opponentDAO.unsave(p1,p2));	
		
		}
		catch(Exception e)
		{
			System.out.println("Exception "+e);
		}
		return null;
	}


}
