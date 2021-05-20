package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mois {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private int mois;
	private int anne;
	private double rendement;
	@ManyToOne
	private Employer employer;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getMois() {
		return mois;
	}


	public void setMois(int mois) {
		this.mois = mois;
	}


	public int getAnne() {
		return anne;
	}


	public void setAnne(int anne) {
		this.anne = anne;
	}


	public double getRendement() {
		return rendement;
	}


	public void setRendement(double rendement) {
		this.rendement = rendement;
	}


	public Employer getEmployer() {
		return employer;
	}


	public void setEmployer(Employer employer) {
		this.employer = employer;
	}


	public Mois(Long id, int mois, int anne, double rendement, Employer employer) {
		super();
		this.id = id;
		this.mois = mois;
		this.anne = anne;
		this.rendement = rendement;
		this.employer = employer;
	}


	public Mois() {
		super();
	}
	
	
	

}
