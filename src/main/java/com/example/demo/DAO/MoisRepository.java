package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employer;
import com.example.demo.entity.Mois;
@Repository
public interface MoisRepository extends JpaRepository<Mois, Long> {
	public Mois findByMoisAndAnneAndEmployerId(int mois,int anne,String id);
	
	//public List<Mois> findByNumeroMoisAnneMoisEmployerId(@Param("numeroMois")int numeroMois,@Param("anneMois")int anneMois,@Param("id")String id);

}
