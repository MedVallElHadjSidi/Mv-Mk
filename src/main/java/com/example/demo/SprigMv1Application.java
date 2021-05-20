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
	
		
	}

	@Override
	public void run(String... args) throws Exception {
	  /* Utilisateur utilisateur1=accountService.addUser(new
		Utilisateur("user1","med","mv@gmail.com","USER_SMHPM","1234SM"));
		Role role1=rolesRepository.save(new Role(null,"USER"));
		accountService.AddRoles("USER_SMHPM","USER");

		Utilisateur utilisateur2=accountService.addUser(new
		Utilisateur("ADM1","med mahmoud","mv@gmail.com","MedMahmoud","1234"));
		Role role=rolesRepository.save(new Role(null,"ADMIN"));
		accountService.AddRoles("MedMahmoud","ADMIN");*/
	}
}
