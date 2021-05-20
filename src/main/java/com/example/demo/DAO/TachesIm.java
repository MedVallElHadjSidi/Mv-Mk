package com.example.demo.DAO;


import java.util.Date;
import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Taches;
@Repository
public interface TachesIm extends JpaRepository<Taches, Long>{
	
	@Query("select distinct d.dateday from Taches d, Employer l where DAYOFWEEK(d.dateday)=2 and d.dateday between d.dateday and :x and DATEDIFF(:x,d.dateday) between 0 and 4 ")
	public Date DebutSemaine(@Param("x")Date d1);
	@Query("select distinct min(t.dateday) from Taches t, Employer er where DAYOFWEEK(t.dateday) between 2 and 6   and t.dateday between t.dateday and :d and DATEDIFF(:d,t.dateday) between 0 and 4 and t.employer.id like:mat and t.employer.id=er.id ")
	public Date DebutRell(@Param("d")Date d1,@Param("mat")String matricule);


	
	
	@Query("select max(b) from Taches b, Employer em where b.employer.id like:x and b.employer.id=em.id")
	public Taches SommeJous(@Param("x")String matricule);
	
	@Query("select t from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public List<Taches> SommeSemaine(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);
	
	

	@Query("select sum(t.nbreSup15) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommeSup15(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);
	
	
	@Query("select sum(t.nbreSup40) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommeSup40(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);
	@Query("select sum(ta.nbreHtNormale) from Taches ta , Employer ep where  ta.dateday between :d1 and :d2 and ta.employer.id like :m and ta.employer.id=ep.id")
	public int SommeHeureNormaleSecuriter(@Param("m")String matricule,@Param("d1")Date d1,@Param("d2")Date d2);

	@Query("select sum(tn.nbreHtNormale) from Taches tn , Employer en where  tn.dateday between :d1 and :d2 and tn.employer.id like :mt and tn.employer.id=en.id")
	public int SommeHN(@Param("d1")Date d1,@Param("d2")Date d2,@Param("mt")String matricule);

	@Query("select sum(t.nbreSup50) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommeSup50(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);

	@Query("select sum(t.nbreSup100) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommeSup100(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);

	@Query("select sum(t.total_Heure) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommeHt(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);

	@Query("select sum(t.panier) from Taches t , Employer e where  t.dateday between :x and :y and t.employer.id like :z and t.employer.id=e.id")
	public int SommePanier(@Param("x")Date d1,@Param("y")Date d2,@Param("z")String matricule);
	

	@Query("select tm from Taches tm, Employer ep where tm.employer.id like:x and tm.employer.id=ep.id")
	public Page<Taches> Chercherbymotcler(@Param("x")String matricule,Pageable pageable);

	public  List<Taches> findByDateday(@Param("date")Date date);

	
	
}
