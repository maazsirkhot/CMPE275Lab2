package com.cmpe275lab2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cmpe275lab2.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
	
	public boolean existsByEmail(String email);
	
	public List<Player> findByEmail(String email);
	
}
