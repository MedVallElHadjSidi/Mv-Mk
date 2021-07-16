package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Parametre;


@Repository
public interface ParametreRepository  extends JpaRepository<Parametre, Long>{
	@Query("select max(b) from Parametre b")
	public Parametre DernierConfiguration();
	

}
