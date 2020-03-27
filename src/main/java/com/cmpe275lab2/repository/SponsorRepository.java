package com.cmpe275lab2.repository;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275lab2.model.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, Long>{
	
	public boolean existsByName(String name);
	public List<Sponsor> findByName(String name); 
}
