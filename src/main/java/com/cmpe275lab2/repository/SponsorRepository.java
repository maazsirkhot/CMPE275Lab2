package com.cmpe275lab2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275lab2.model.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, Long>{
<<<<<<< HEAD
	List<Sponsor> findByName(String name);

	boolean existsByName(String id);
=======
	
	public boolean existsByName(String name);
	public List<Sponsor> findByName(String name);
	
>>>>>>> master
}
