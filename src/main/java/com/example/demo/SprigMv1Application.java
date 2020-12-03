package com.example.demo;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.demo.DAO.RoleRepository;
import com.example.demo.DAO.UtilisateurRepository;
import com.example.demo.entity.Role;
import com.example.demo.entity.Utilisateur;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SprigMv1Application implements CommandLineRunner {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RoleRepository rolesRepository;

	@Autowired
	private AccountService accountService;
	@Bean
	public BCryptPasswordEncoder getBcrypte(){
		return  new BCryptPasswordEncoder();
	}



	public static void main(String[] args) throws ParseException {
		SpringApplication.run(SprigMv1Application.class, args);
	
		
		/*
		 * EmployerIm em=ctx.getBean(EmployerIm.class); IMetier
		 * emet=ctx.getBean(IMetier.class);
		 * 
		 * TachesIm tacheim=ctx.getBean(TachesIm.class); DateFormat formatter = new
		 * SimpleDateFormat("hh:mm"); DateFormat formatter2 = new
		 * SimpleDateFormat("hh:mm"); Employer emp1=em.save(new Employer("Mv",
		 * "Med","macon")); Date d1; SimpleDateFormat formattejr = new
		 * SimpleDateFormat("yyyy-MM-dd"); Date d5=new Date(); String
		 * d6=formattejr.format(d5); Date dj; try { d1 = formatter.parse("08:00"); Date
		 * d2=formatter2.parse("17:00"); Date d3=formatter.parse("20:00"); Date
		 * d4=formatter.parse("23:00"); dj=formattejr.parse(d6);
		 * 
		 * Taches tache1=new Taches(new Date(), d1, d2);
		 * 
		 * 
		 * Taches tache2=new Taches(new Date(), d3, d4); tache2.setDateday(dj); //
		 * Taches t3=emet.MemeJours(tache2); tache1.setEmployer(emp1);
		 * tache2.setEmployer(emp1);
		 * 
		 * Taches t=tacheim.save(tache1); Taches t2=tacheim.save(tache2); } catch
		 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 * 
		 * System.out.println(em.existsById("Mv"));
		 * 
		 */
		  
		 
	
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		/*Utilisateur utilisateur1=accountService.addUser(new
		Utilisateur("user1","med","mv@gmail.com","MEDVALL","1234"));
		Role role1=rolesRepository.save(new Role(null,"USER"));
		accountService.AddRoles("MEDVALL","USER");

		Utilisateur utilisateur2=accountService.addUser(new
		Utilisateur("ADM1","med mahmoud","mv@gmail.com","MedMahmoud","1234"));
		Role role=rolesRepository.save(new Role(null,"ADMIN"));
		accountService.AddRoles("MedMahmoud","ADMIN");*/
	}
}
