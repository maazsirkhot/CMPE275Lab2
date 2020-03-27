package com.cmpe275lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe275lab2.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
	
	public boolean existsByEmail(String email);
}
