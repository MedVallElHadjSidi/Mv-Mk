package com.example.demo.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DAO.ParametreRepository;
import com.example.demo.entity.Parametre;

@Controller

public class ParametreControlleur {
	@Autowired
	private ParametreRepository parametreRepository;
	
	@PostMapping(value = "/addparemtre")
	
	private String  AddParametre(@Valid  Parametre parametre, Model model) {
		Parametre parametre2 =parametreRepository.DernierConfiguration();
		
		System.out.println("heure noraml"+parametre2.getHeure_Normale());
		for(int i=0;i<parametre2.getWeekends().length;i++) {
			System.out.println("weekend "+i+1+""+":"+" "+parametre2.getWeekends()[i]);
		}
		String valider="";
		String nonvalide="";
		String message="";
		 Parametre param =parametreRepository.save(parametre);
		 if(param!=null) {
			
			 valider="images/valider.jpg";
				nonvalide=null;
				message="L'ajout de l'employer "+" "+"avec success";
			 
		 }
		 else
		 {
			 nonvalide="images/dow22.png";
				valider=null;
				
				message="desoler cet matricule existe deja!";
		 }
		 model.addAttribute("valider", valider);
			model.addAttribute("nv", nonvalide);
			model.addAttribute("matricule", message);
		 return "addparametre";
	}

}
